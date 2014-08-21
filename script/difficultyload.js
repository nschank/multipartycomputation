$(function() {
				$( "#slider" ).slider({
					value: difficulty,
					min: 1,
					max: 6,
					step: 1,
					slide: function( event, ui ) {
						setDifficulty(ui.value);
						updateGlossary(ui.value);
						updateEquations(ui.value);
					}
				})});