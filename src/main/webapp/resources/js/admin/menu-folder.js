//menu 효과
	const expandMenu = document.querySelector('#expandMenu');
	const foldMenu = document.querySelector('#foldMenu');
	const subMenuByMenu = document.querySelector('#subMenuByMenu');
	expandMenu.addEventListener('click',(e)=>{
		expandMenu.style.display = 'none';	
		foldMenu.style.display = 'block';
		subMenuByMenu.style.display = 'block';
	});
	foldMenu.addEventListener('click',(e)=>{
		expandMenu.style.display = 'block';	
		foldMenu.style.display = 'none';
		subMenuByMenu.style.display = 'none';
	});
	
//sideMenu 효과
	const expandSideMenu = document.querySelector('#expandSideMenu');
	const foldSideMenu = document.querySelector('#foldSideMenu');
	const subMenuBySideMenu = document.querySelector('#subMenuBySideMenu');
	expandSideMenu.addEventListener('click',(e)=>{
		expandSideMenu.style.display = 'none';	
		foldSideMenu.style.display = 'block';
		subMenuBySideMenu.style.display = 'block';
	});
	
	foldSideMenu.addEventListener('click',(e)=>{
		expandSideMenu.style.display = 'block';	
		foldSideMenu.style.display = 'none';
		subMenuBySideMenu.style.display = 'none';
	});
	
//sideSubMenu 효과
	const expandSideSubMenu = document.querySelector('#expandSideSubMenu');
	const foldSideSubMenu = document.querySelector('#foldSideSubMenu');
	const subMenuBySideSubMenu = document.querySelector('#subMenuBySideSubMenu');
	expandSideSubMenu.addEventListener('click',(e)=>{
		expandSideSubMenu.style.display = 'none';	
		foldSideSubMenu.style.display = 'block';
		subMenuBySideSubMenu.style.display = 'block';
	});
	
	foldSideSubMenu.addEventListener('click',(e)=>{
		expandSideSubMenu.style.display = 'block';	
		foldSideSubMenu.style.display = 'none';
		subMenuBySideSubMenu.style.display = 'none';
	});