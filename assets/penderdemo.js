
//==============================================================================


function Cat() {

    this.frame = 0;

    //this.xspeed = 0.01 * Math.floor(Math.random()*5);
    //this.yspeed = 0.01 * Math.floor(Math.random()*5);

    this.xpos = 50;
    this.ypos = 50;

    //this.rotation = 0.0;

    //this.scalefactor = 0.5;

    this.drawme = function () {

        if (this.frame > 10) { this.frame = 0; }

    //    this.xpos += this.xspeed;
    //    this.ypos += this.yspeed;
    //    if (this.xpos < -1.0 || this.xpos > 1.0) {
    //        this.xspeed *= -1;
    //    }
    //    if (this.ypos < -1.0 || this.ypos > 1.0) {
    //        this.yspeed *= -1;
    //    }

        this.frame += 1;
      //  Canvas.scale(0.5, 0.5);
        Canvas.translate(10,10);

        Canvas.drawImage(this.frame, 0, 0);

    };

}

//==============================================================================

var cats = [];
var catnumb = 8;

//==============================================================================

function init() {

    var i = 0;

    for (i = 0; i < catnumb; i += 1) {
        console.log("it's a new cat!");
        cats[i] = new Cat();
    }

}

//==============================================================================

function draw() {

    var i = 0;

    for (i = 0; i < catnumb; i += 1) {
        cats[i].drawme();

    }

}

//==============================================================================