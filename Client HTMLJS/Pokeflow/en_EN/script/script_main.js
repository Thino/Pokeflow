$(document).ready(function(){
	$('nav li').on('mouseenter', function(){
		$(this).stop().animate({
			top : '-5px'
		},'fast');
		$(this).css({
			boxShadow: '0 2px 2px rgba(0, 0, 0, 0.5)'
		});
	});
	
	$('nav li').on('mouseleave', function(){
		$(this).stop().animate({
			top : '0px'
		},'fast');
		$(this).css({
			boxShadow: 'none'
		});
	});
});