function popupTarefaConcluida(id){
	$( "#confirm" ).popup( "open" );
	$( "#confirm #yes" ).attr("href", "concluir-tarefa?id="+id);
	
	$( "#confirm #no" ).on( "click", function() {
    	$( "#confirm" ).popup( "close" );
    });
}

function popupCriarGrupo(){
	$( "#groupCreate" ).popup( "open" );
	
	$( "#groupCreate #no" ).on( "click", function() {
    	$( "#groupCreate" ).popup( "close" );
    });
}