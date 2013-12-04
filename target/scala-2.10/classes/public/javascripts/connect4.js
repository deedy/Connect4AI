
function Connect4(id, s, wb, hb, bw) {
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
}

Connect4.prototype.playMove = function(column, row, turn) {
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
    fill: fillcolor
  });
  c.click((function(a, context) {
    return function() {
      context.clickCallback(a);
    }
  })(column, this));
  this.board.add(c);
  c.animate({cy : yfinal}, 1000, mina.bounce);
}

Connect4.prototype.clickCallback = function(column) {
  var r = jsRoutes.controllers.Application.playMoveInGame(this.id);
  console.log(this);
  $.ajax({
    url: r.url,
    context: this,
    type: r.type,
    data: {
      "column" : column
    },
    dataType: "json",
    success: function(e) {
      console.log("success");
      console.log(e);
      console.log(this);
      this.playMove(e.column, e.row, e.turn);
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
        console.log(moves[i]);
        this.playMove(moves[i].column, moves[i].row, moves[i].turn);
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
  var instance = new Connect4(gameId, s, 7, 6, 60);
  console.log(instance);
  // var r = jsRoutes.controllers.Application.simpleAdder();
  // $.ajax({
  //   url: r.url,
  //   type: r.type,
  //   data: {
  //     9 : {"value1":"kaum", "bitches":"shera"},
  //     8 : "value2"
  //   },
  //   success: function(e) {
  //     console.log("success");
  //     console.log(e);
  //   },
  //   error: function() {
  //     console.log("error");
  //   }
  // });
  // console.log(r.url);
});
