/**
 * Copyright 2012 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/**
 * Pender Client Demo
 * 
 * basic demo of client app using Pender
 *
 * Lorin Beer
 * lorin@adobe.com 
 */


/**
 * container for art resource meta data
 */
var buildbotmeta = {
	"maxbots" : 50,
    "framemap" : "client/assets/build_bot_map.png",
    "texid" : 0,
    "framenumb" : 9,
    "border" : 1,
    "columns" : 4,
    "rows" : 3,
    "height" : 126,
    "width" : 126
};



/**
 * 2D coordinate
 */
function Point(x, y) {
    this.x  = x || 0;
    this.y = y || 0;
}


/**
 * storage class for animation data
 */
function Animation(data, initialframe) {
    this.currentframe = initialframe || 0; //optional parameter
    this.frames = []; //top left texture coordinate for each frame
    //copy the data out
    this.framemap = data.framemap; //framemap matching the data
    this.texid = data.texid;
    this.framenumb = data.framenumb;
    this.borderwidth = data.border;
    this.cols = data.columns;
    this.rows = data.rows;
    this.height = data.height;
    this.width = data.width;

    /**
     * currently oscillates, can be changed to provide custom frame updating
     * on a per Animation basis
     */
    this.frameupdate = function () {
        this.currentframe += 1;
        if (this.currentframe >= this.frames.length) {
            this.currentframe = -(this.currentframe - 1);
        }
    };

    /**
     * initialize frame coordinates
     *  calculates top left corner of each frame based on animation data
     */
    this.initFrames = function (framenumb) {
        var i = 0,
            done = false,
            row = 0,
            col = 0,
            tx = 0,
            ty = 0;

        for (row  = 0; row < this.rows && !done; row += 1) {
            for (col = 0; col < this.cols && !done; col += 1) {
                tx = col * (this.height + 1) + (col + 1) * this.borderwidth;
                ty = row * (this.width + 1) + (row + 1) * this.borderwidth;
                this.frames.push(new Point(tx, ty));
                i += 1;
                if (i >= framenumb) {
                    done = true;
                }
            }
        }
    }; //end initFrames

    //force initialization of frames on creation
    this.initFrames(this.framenumb);

    /**
     *
     */
    this.draw = function (dx, dy, dWidth, dHeight) {
        //absolute value of current frame index is used
        var curframe = this.currentframe < 0 ? this.currentframe * -1 :
                                               this.currentframe,
        frametop = this.frames[curframe];

        Pender.canvas.drawImage(Pender.getImage(this.texid),
                                frametop.x,
                                frametop.y,
                                this.width,
                                this.height,
                                dx,
                                dy,
                                dWidth,
                                dHeight);
        this.frameupdate();
    }; //end draw
}

/**
 * animated screen entity
 */
function AnimatedSprite(anim) {
    //2d coordinate of sprite
    this.xpos = 0;
    this.ypos = 0;
    //2d velocity of sprite
    this.xvel = Math.random() * 10 + 5;
    this.yvel = Math.random() * 10 + 5;
    //size of sprite, allows scaling of animdata texture
    this.width = 128;
    this.height = 128;

	this.counter = 0;
	this.maxcount = 7;

    //animation associated with this sprite can differ on a sprite by sprite basis
    this.animation = anim;
    //self reference
    var self = this;
    /**
     * update the sprite by time delta
     */
    this.update = function (delta) {

    };
    /**
     * draw this sprite at the current location
     */
    this.draw = function (deltat) {
        //update sprite position based on velocity
        self.xpos = self.xpos + (self.xvel * (deltat/100)) ;
        self.ypos = self.ypos + (self.yvel * (deltat/100));
        //check x borders
        if (self.xpos < 0 || self.xpos >= Pender.width - self.width) {
            self.xvel = self.xvel * -1;
        }
        //check y borders
        if (self.ypos < 0 || self.ypos >= Pender.height - self.height) {
            self.yvel = self.yvel * -1;
        }
        //call the animations draw function with coordinates and size
        self.animation.draw(self.xpos, self.ypos, self.width, self.height);
    };
}

/**
 * global object containing the important parts of our app
 */
var Bots = function () {
    // number of bots to be displayed
    this.numb = 1;
    // array containing the Bot objects
    this.bots = [];
    // the texture id generated by Pender for the bot framemap
    this.texid = 0;
    //
    this.lastframe = 0;
    // 
    this.fps = 0.0;

    this.counter = 0;
    
    this.deltat = 0.0;
    
    this.maxcount = 12;

    //self reference
    var self = this;
    /**
     * initialize Bots with current settings
     */
    this.init = function () {
        var i = 0,
            anim = null;
        //load the art resources specified in the meta data
        self.texid = Pender.loadImage(buildbotmeta.framemap);
        buildbotmeta.texid = self.texid; //update the texid before creating any Animations
        //create our bots
        for (i = 0; i < self.numb; i += 1) {
            this.addbot();
        }
    };

	this.addbot = function () {
		anim = new Animation(buildbotmeta); //buildbotmeta contains all the data required
        //push a new bot, initialized with the animation just created
        self.bots.push(new AnimatedSprite(anim));
	}

	this.spawnbot = function () {
		anim = new Animation(buildbotmeta); //buildbotmeta contains all the data required
        //push a new bot, initialized with the animation just created
        var sprite = new AnimatedSprite(anim);
 //       sprite.xpos = self.bots[0].xpos;
   //     sprite.ypos = self.bots[0].ypos;

        self.bots.push(sprite);     
	}
	
    /**
     * redraw the scene
     */
    this.draw =  function () {
        var i = 0;
        
        self.counter += 1;
        if (self.counter >= self.maxcount && self.numb <= buildbotmeta.maxbots) {
            self.spawnbot();
            self.numb += 1;
            self.counter = 0;
        }
        self.updateframerate();
        //clear the entire screen (no blitting yet)
        Pender.canvas.clearRect(0, 0, Pender.width, Pender.height);
        //draw each bot
        for (i = 0; i < self.numb; i += 1) {
            self.bots[i].draw(self.deltat);
        }
    };
	
	/**
	 * 
	 */
    this.updateframerate = function () {
        var nowframe = new Date().getTime(),
       	    framefps = Math.round(1.0 / (nowframe - self.lastframe) * 1000.0);
        this.deltat = nowframe - self.lastframe;
        self.lastframe = nowframe;
		self.fps = Math.round ((self.fps * 0.9) + (0.1 * framefps)); //weighted averaging
		if (self.counter == 8) { 
			console.log("FPS: " + self.fps + "   Bots: " + this.numb);
		}
    };
};

/**
 * main entry point for Pender
 */
function init() {
    //initialize our global object
    var bots = new Bots();
    bots.init();
    //register our draw function and the desired interval with Pender
    Pender.setInterval(bots.draw, 50);
}
