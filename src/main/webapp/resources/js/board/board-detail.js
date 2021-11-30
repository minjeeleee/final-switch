function transfer(e) {
		let downloadFile = (ofn,rfn,path) => {
			let paramObj = {
					originFileName : ofn,
					renameFileName : rfn,
					savePath : path
			}
			location.href = '/download?' + urlEncoder(paramObj);
		}
}

