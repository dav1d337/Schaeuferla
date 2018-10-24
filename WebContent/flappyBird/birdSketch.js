var bird;
var pipes = [];

function setup() {
	var cnv = createCanvas(windowWidth, windowHeight);
	cnv.style('display', 'block');
	cnv.position(0, 0);
	pipes.push(new Pipe());
	bird = new Bird();

}

function draw() {
	background(0);

	for (var i = pipes.length - 1; i >= 0; i--) {
		pipes[i].show();
		pipes[i].update();

		if (pipes[i].hits(bird)) {
			console.log("HIT");
		}

		if (pipes[i].offscreen()) {
			pipes.splice(i, 1);
		}
	}

	bird.update();
	bird.show();

	if (frameCount % 75 == 0) {
		pipes.push(new Pipe());
	}
}

function keyPressed() {
	if (key == 'w') {
		bird.up();
		// console.log("SPACE");
	}
}