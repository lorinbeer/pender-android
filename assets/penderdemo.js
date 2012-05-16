//==============================================================================
function Point(x,y) {
    this.x  = x || 0;
    this.y = y || 0;
}
//==============================================================================

/**
 * storage class for animation data
 */
function Animation(framemap, framenumb, cols, rows, framewidth, frameheight, bordersize, initialFrame ) {
    this._currentframe = initialFrame || 0;
    this._frames = [];
    this._framemap = framemap;
    this._framewidth = framewidth;
    this._frameheight = frameheight;
    this._borderwidth = bordersize;

    /**
     * currently oscillates, can be changed to provide custom frame updating
     * on a per AnimatedSprite basis
     */
    this._frameupdate= function() {
	
	this._currentframe += 1;
	if( this._currentframe >= this._frames.length ) { this._currentframe = -(this._currentframe-1); }

    }
   
    this.initFrames = function(framenumb) {
	    var i = 0;
	    var done = false;
	    var top = new Point(0,0);
	    
	    for (var row  = 0; row < rows && !done; row++) {
		for (var col = 0; col < cols && !done; col++) {
		    top = new Point( col * (frameheight+1)+(col+1)*bordersize, row * (framewidth+1) +(row+1)* bordersize);
		    console.log(col*frameheight,(col+1)*bordersize);
		    this._frames.push ( top );
		    i+=1;
		    if( i >= framenumb ) { done = true; }
		}
	    }
    }
    this.initFrames( framenumb );


    this.draw = function(ctx, dx, dy, dWidth, dHeight) {
	//absolute value of current frame index is used, allowing for negative frame counts
	var curframe = this._currentframe<0? -1 * this._currentframe : this._currentframe;
	var frametop = this._frames[curframe];
	Pender.ctx.drawImage( this._framemap, frametop.x, frametop.y, this._framewidth, this._frameheight, dx, dy, this._framewidth, this._frameheight   );
	this._frameupdate();
    }
}





function AnimatedSprite(anim) {

    this._animation = anim;


    this.init = function (framemap, framenumb, cols, rows, framewidth, frameheight) {
	var i = 0;
	var done = false;
	var top = new Point(0,0);
	
	//initialize
	this._framemap = framemap;
	this._framewidth = framewidth;
	this._frameheight = frameheight;
	/*
	for (var row  = 0; row < rows && !done; row++) {
	    for (var col = 0; col < cols && !done; col++) {
		i+=1;
		if( i >= framenumb ) { done = true; }
		var top = new Point(col * frameheight+col+1, row * framewidth+row+1);
		var bot = new Point(top.x + frameheight, top.y + framewidth);

		this._frames.push ( [top,bot] );
	    }
	}*/
	this._frames;

    }

}

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

        Canvas.translate(this.xpos, this.ypos);


        Canvas.drawImage(this.frame, 0, 0);

    };

}

//==============================================================================

//==============================================================================


//==============================================================================

function draw() {

}

function init() {
    console.log("loading images");
    console.log("whatwhat");
    console.log(draw);
}