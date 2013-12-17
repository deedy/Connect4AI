
function Connect4(id, s, wb, hb, bw, pli) {
  /**
   * Whether or not this should be an AI vs AI testing game.
   */
  this.playItself = pli;
  /**
   * The Game Id as stored on the server
   */
  this.id = id;
  /**
   * The Snap SVG instance
   */
  this.s = s;
  /**
   * The width (in number of holes) of the Connect4 board
   */
  this.widthBlock = wb;
  /**
   * The height (in number of holes) of the Connect4 board
   */
  this.heightBlock = hb;
  /**
   * Block width (in pixels)
   */
  this.blockWidth = bw;


  this.boardWidth = this.widthBlock  * this.blockWidth;
  this.boardHeight = this.heightBlock  * this.blockWidth;
  this.circlescale = 0.8;
  this.radius = this.blockWidth * this.circlescale / 2;
  /**
   * Color of player A's piece (in hex)
   */
  this.colorA = "#F5E50A"
  /**
   * Color of player B's piece (in hex)
   */
  this.colorB = "#FD0A06";
  /**
   * Color of the board (in hex)
   */
  this.boardColor = "#bada55";
  /**
   * The underlying Snap SVG groups that make up the board.
   */
  this.board = null;
  /**
   * Call to initialize the board
   */
  this.drawBoard();
  /**
   * Private function to make the class selector for a filled coin
   */
  this.makeSelector = function(x, y) {
      return "coin-"+x+"-"+y;
  }
  if (this.playItself) {
    this.initiateArtificialIntelligence();
  }
}
Connect4.prototype.getCoinAt = function(row, column) {
  var selector = "."+this.makeSelector(row, column);
  return this.board.select(selector);
}

Connect4.prototype.playMove = function(datastruct) {
  console.log(datastruct);
  if (datastruct.hasOwnProperty("message") &&
      datastruct.hasOwnProperty("messageCode") ) {
    $("#message").text(datastruct["message"]);
    $("#message").show();
    $("#new-game").show();

  } else {
    $("#message").hide();
    $("#new-game").hide();
  }
  if (datastruct.hasOwnProperty("row") &&
      datastruct.hasOwnProperty("column") &&
      datastruct.hasOwnProperty("turn")) {
    var row = datastruct["row"];
    var column = datastruct["column"];
    var turn = datastruct["turn"];
    if (row == -1) {
      // TODO: no possible play in this column
      return;
    }
    var xoffset = ((column + 1) - 0.5) * this.blockWidth + (this.circlescale/2);
    var yinit = 0;
    var yfinal = (this.heightBlock - row - 0.5) * this.blockWidth + (this.circlescale/2);

    var c = this.s.circle(xoffset, 0, this.radius);
    var fillcolor = turn % 2 == 0? this.colorA : this.colorB;
    c.attr({
      fill: fillcolor,
      class: this.makeSelector(row, column)
    });
    c.click((function(a, context) {
      return function() {
        context.clickCallback(a);
      }
    })(column, this));
    this.board.add(c);
    c.animate({cy : yfinal}, 1000, mina.bounce);
  }
  if (datastruct.hasOwnProperty("winstreak")) {
    this.drawWin(datastruct['winstreak']);
  }
}

Connect4.prototype.drawWin = function(points) {
  for (var i = 0; i < points.length; i++) {
    var circle = this.getCoinAt(points[i]['row'], points[i]['column']);
    if (circle != null) {
      circle.attr("stroke", "#000");
      circle.attr("stroke-width", "4");
    }
  }
}

Connect4.prototype.initiateArtificialIntelligence = function() {
  var ai_r = jsRoutes.controllers.Application.playMoveInGameAI(this.id)
  $.ajax({
    url: ai_r.url,
    context: this,
    type: ai_r.type,
    dataType: "json",
    timeout: 30000,
    success: function(move){
      console.log("success AI");
      console.log(move);
      this.playMove(move);
      if (this.playItself) {
        this.initiateArtificialIntelligence();
      }
    },
    error: function() {
      console.log("error AI");
    },
    complete: function(xhr, retcode) {
      if (retcode == "timeout") {
        console.log("request timeout, trying again.");
        this.initiateArtificialIntelligence();
      }
    }
  });
}

Connect4.prototype.clickCallback = function(column) {
  var r = jsRoutes.controllers.Application.playMoveInGame(this.id);
  $.ajax({
    url: r.url,
    context: this,
    type: r.type,
    data: {
      "column" : column
    },
    dataType: "json",
    success: function(move) {
      console.log("success");
      console.log(move);
      this.playMove(move);
      this.initiateArtificialIntelligence();
    },
    error: function() {
      console.log("error");
    }
  });
}

Connect4.prototype.drawBoard = function() {
  this.board = this.s.group();
  this.board.add(this.s.rect(0,0,this.boardWidth, this.boardHeight));
  this.board.attr({
    fill: this.boardColor
  })

  var circles = this.s.group();
  for (var i = 1; i <= this.widthBlock; i++) {
    for (var j = 1; j <= this.heightBlock; j++) {
      var xoffset = (i - 0.5) * (this.boardWidth / this.widthBlock) + (this.circlescale/2);
      var yoffset = (j - 0.5)*(this.boardHeight/ this.heightBlock) + (this.circlescale/2);
      var circle = this.s.circle(xoffset, yoffset, this.radius);
      circle.click((function(a, context) {
        return function() {
          context.clickCallback(a);
        }
      })(i-1, this));
      circles.add(circle);
    }
  }
  circles.attr({
    fill: "#fff",
    class: "empty-circle"
  });
  this.board.add(circles);
  this.board.drag();
  this.retrieveBoard();
}

Connect4.prototype.retrieveBoard = function() {
  var r = jsRoutes.controllers.Application.getGameBoard(this.id);
  $.ajax({
    url: r.url,
    context: this,
    type: r.type,
    dataType: "json",
    success: function(moves) {
      console.log("success in retrieval");
      for (var i = 0; i < moves.length; i++) {
        this.playMove(moves[i]);
      }
    },
    error: function() {
      console.log("error in retrieval");
    }
  });
}

$(document).ready(function() {
  var gameId = $('#gameId').data('id');
  var s = Snap($(document).width(), $(document).height());
  var instance = new Connect4(gameId, s, 7, 6, 60, false);
});
