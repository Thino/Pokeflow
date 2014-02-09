$(document).ready(function(){	

	$('#top li').eq(0).on('click', function() {
		$('#main_section_edit').fadeOut('fast', function() {	
			$('#main_section_info').fadeIn('fast');		
		});
	});
	
	$('#top li').eq(1).on('click', function() {
		$('#main_section_info').fadeOut('fast', function() {	
			$('#main_section_edit').fadeIn('fast');	
		});			
	});

});