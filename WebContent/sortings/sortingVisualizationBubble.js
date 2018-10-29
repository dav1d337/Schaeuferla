let numbers, columnWidth, sorter, t0;

let n = 15;
let sel;
let button;
let cnv;

function setup() {

	  
	cnv = createCanvas(windowWidth, windowHeight);
	cnv.style('display', 'block');
	cnv.position(0, 0);

	  
	numbers = Array(n).fill().map(() => random(1));
	columnWidth = width / numbers.length;
	
	sorter = bubbleSort();
	t0 = performance.now();
	
// button = createButton('reset');
// button.position(10, 15);
// button.mousePressed(resetArray);


}


// resetArray = function () {
//	
// sorter = selectionSort();
// console.log('is now');
//	
//	
// console.log('resetting')
// numbers = Array(n).fill().map(() => random(1));
// columnWidth = width / numbers.length;
// t0 = performance.now();
//
// loop();
// }

function draw() {
	background(0);
	textSize(25);
	

 for (let i = 0; i < numbers.length; i++) {
	 	let columnHeight = map(numbers[i], 0, 1, 0, height);
	 	let col = color(numbers[i], height, height);
	 	rect(i * columnWidth, height, columnWidth, -columnHeight);
	 	// colorMode(HSB, height);
	 	stroke(col);
	 	fill(col);
 }

	
	if (sorter.next().done) {
		let time = round(performance.now() - t0) / 1000;
		print("Done!");
		print(`Sorted ${numbers.length} elements in approximately ${time} seconds `);
		colorMode(RGB, height);
		
		text(`Sorted ${numbers.length} elements in approximately ${time} seconds.`, 90,20);
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

// function* selectionSort() {
// for (j = 0; j < numbers.length - 1; j++) {
// let a = numbers[j];
// let b = numbers[j + 1];
// if (a > b) {
// swap(numbers, j, j + 1);
// }
// console.log('bla');
// }
// // if (loops == numbers.length) finished = true;
// }
//
// swap = function (arr, a, b) {
// let temp = arr[a];
// arr[a] = arr[b];
// arr[b] = temp;
// }
