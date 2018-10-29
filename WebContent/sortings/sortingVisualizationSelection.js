let values = [];
let loops;
let swaps;
let cycles = 1;
let lines = false;
let numLines = 25;
let selType, inpNum;
let curPivot, curHi, curLo;
let button;
let finished = false;
let qsCalled = false;
let t0;
let cnv;

function setup() {
	selType = createSelect();
	selType.position(10, 0);
	selType.option('Bubble');
	selType.option('Selection');
	selType.option('QuickSort');

	inpNum = createInput('25');
	inpNum.position(10, 55);
	inpNum.input(function () {
		numLines = inpNum.value();
		resetArray();
	});

	button = createButton('Restart');
	button.position(10, 120);
	button.mousePressed(resetArray);

	cnv = createCanvas(windowWidth, windowHeight-190);
	cnv.style('display', 'block');
	cnv.position(0, 180);
	colorMode(HSB, height);
	resetArray();

}

function draw() {
	background(255);

	if (!finished) {
		switch (selType.value()) {
			case 'Bubble':
				bubbleSort();
				qsCalled = false;
				break;
			case 'Selection':
				selectionSort();
				qsCalled = false;
				break;
			case 'QuickSort':
				if (!qsCalled) {
					quicksort(values, 0, values.length - 1);
					qsCalled = true;
				}
				break;
			default:
				bubbleSort();
				break;
		}
	} else {
		let time = round(performance.now() - t0) / 1000;
		print("Done!");
		print(`Sorted ${values.length} elements in approximately ${time} seconds `);
		colorMode(RGB, height);
		textSize(30);
		text(`Sorted ${values.length} elements in approximately ${time} seconds.`, 90,40);
		
		console.log("finished");
		noLoop();
	}

	for (i = 0; i < values.length; i++) {
		let col = color(values[i], height, height);
		let location = map(i, 0, values.length, 0, width);
		colorMode(RGB);
		switch (i) {
			case curHi:
				stroke(0);
				fill(0, 255, 0);
				break;
			case curLo:
				stroke(0);
				fill(0, 0, 255);
				break;
			case curPivot:
				stroke(0);
				fill(255);
				break;
			default:
				stroke(col);
				fill(col);
				break;
		}
		colorMode(HSB, height);
		rect(location, height - values[i], width / numLines, height);

	}
}

swap = function (arr, a, b) {
	let temp = arr[a];
	arr[a] = arr[b];
	arr[b] = temp;
}

bubbleSort = function () {
	if (swaps < values.length - loops - 1) {

		if (numLines >= 50) cycles = 10;
		else if (numLines >= 200) cycles = 50;
		else if (numLines >= 500) cycles = 100;

		for (i = 0; i < cycles; i++) {
			let a = values[swaps];
			let b = values[swaps + 1];
			if (a > b) {
				swap(values, swaps, swaps + 1);
			}
			swaps++;
		}
	} else {
		swaps = 0;
		loops++;
	}
	if (loops == values.length) finished = true;
}

selectionSort = function () {
	for (j = 0; j < values.length - loops - 1; j++) {
		let a = values[j];
		let b = values[j + 1];
		if (a > b) {
			swap(values, j, j + 1);
		}
	}
	if (loops == values.length) finished = true;
}

quicksort = function (arr, lo, hi) {
	setTimeout(() => {
		if (lo < hi) {
			let mid = partition(arr, lo, hi);
			curPivot = mid;
			curHi = hi;
			curLo = lo;
			quicksort(arr, lo, mid - 1);
			quicksort(arr, mid + 1, hi);
		}
	}, 500);
}

partition = function (arr, low, high) {
	let pivot = arr[high];
	let i = (low - 1);
	for (let j = low; j <= high - 1; j++) {
		if (arr[j] <= pivot) {
			i++;
			swap(arr, i, j);
		}
	}
	swap(arr, i + 1, high);
	return (i + 1);
}

resetArray = function () {
	console.log('resetting')
	t0 = performance.now();
	values = [];
	for (i = 0; i < numLines; i++) {
		values[i] = random(height);
	}
	loops = 0;
	swaps = 0;
	qsCalled = false;
	finished = false;
	loop();
	
}