let numbers, columnWidth, sorter, t0;

function setup() {
	var cnv = createCanvas(windowWidth, windowHeight);
	cnv.style('display', 'block');
	cnv.position(0, 0);
	numbers = Array(50).fill().map(() => random(1));
	columnWidth = width / numbers.length;
	sorter = bubbleSort();
	t0 = performance.now();

	fill(255);
	noStroke();
}

function draw() {
	background(0);

	for (let i = 0; i < numbers.length; i++) {
		let columnHeight = map(numbers[i], 0, 1, 0, height);
		rect(i * columnWidth, height, columnWidth, -columnHeight);
	}

	if (sorter.next().done) {
		let time = round(performance.now() - t0) / 1000;
		print("Done!");
		print(`Sorted ${numbers.length} elements in approximately ${time} seconds.`);
		noLoop();
	}
}

function* bubbleSort() {
	for (let i = numbers.length - 1; i > 0; i--) {
		for (let j = 0; j < i; j++) {
			if (numbers[j] > numbers[j + 1]) {
				// swap
				let t = numbers[j];
				numbers[j] = numbers[j + 1];
				numbers[j + 1] = t;
			}
			yield;
		}
	}
	
}