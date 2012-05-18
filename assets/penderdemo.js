//==============================================================================
//==============================================================================
var buildbotmeta = {
    borderwidth : 1
}


function Point(x,y) {
    this.x  = x || 0;
    this.y = y || 0;
}


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
		 
		    this._frames.push ( top );
		    i+=1;
		    if( i >= framenumb ) { done = true; }
		}
	    }
    }
    this.initFrames( framenumb );


    this.draw = function(texid, dx, dy, dWidth, dHeight) {
	
	//absolute value of current frame index is used, allowing for negative frame counts
	var curframe = this._currentframe<0? -1 * this._currentframe : this._currentframe;
	var frametop = this._frames[curframe];
	
	Canvas.drawImage(Pender.getImage(texid), frametop.x, frametop.y, this._framewidth, this._frameheight,
			     dx, dy, dWidth, dHeight);

	//Pender.ctx.drawImage( Pender.getImage(0), frametop.x, frametop.y, this._framewidth, this._frameheight, dx, dy, this._framewidth, this._frameheight   );
	this._frameupdate();
    }


}

function AnimatedSprite(anim) {
    this.xpos = 0;
    this.ypos = 0;
    this.xvel = Math.random()*5;
    this.yvel = Math.random()*5;
    this.width = 128;
    this.height = 128;
    this._animation = anim;
    var self = this;
    
   this.draw = function(texid) {
	self.xpos = self.xpos+ self.xvel;
	self.ypos = self.ypos+ self.yvel;
	if( self.xpos < 0 || self.xpos >= 720-self.width/*Pender.canvas.width*/) {
	    self.xvel = self.xvel * -1; 
	}
	if(self.ypos < 0 || self.ypos >= 1084-self.height/*Pender.canvas.height */) { 
	    self.yvel = self.yvel * -1; 
	}
	
	self._animation.draw( texid, self.xpos, self.ypos, self.width, self.height );
    };
}


var Bots = new function () {
    this.numb = 10;
    this.bots = [];
    this.texid = 0;
    var self = this;
    this.init = function() {
	console.log("initing");
        self.texid = Pender.loadImage( "client/assets/build_bot_map.png" );
	for (var i = 0; i < self.numb; i++) {
	    
	    var anim = new Animation (self.texid, 9, 4, 3, 254, 254, 1);
	    self.bots.push (new AnimatedSprite(anim));
	}
    };
    
    this.draw =  function() {
	Canvas.clearRect(0,0,400,400 );
      	for(var i = 0; i < self.numb; i++) {
	    self.bots[i].draw(self.texid);    	     
	}
    };
    
}


function init() {
    console.log("INIT CALLED");
    Bots.init();
}


function draw() {
    Bots.draw();
}