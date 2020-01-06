jQuery.fn.tablefilter = function () {
	var markon = "ON";
	var markoff = "OFF";
	$("body").append("<div id=droplist>");
	return this.each(function () {
		$(this).find("th").append("<input class='btfilter' type='button'>");
		$(".btfilter").val(markon);
		$(".btfilter").click(function () {
			thisbutton = $(this);
			if (thisbutton.val() == markon) {
				var thindex = thisbutton.parent("th").index();
				var choices = [];
				thisbutton.parents("table").find("td").each(function () {
					if ($(this).index() == thindex) {
						choices.push($(this).text());
					}
				});
				choices.sort();
				var uniquechoices = [choices[0]];
				for (var i = 1; i < choices.length; i++) {
					if (choices[i - 1] != choices[i]) {
						uniquechoices.push(choices[i]);
					}
				}
				choices = uniquechoices;
				$("#droplist").empty();
				var pos = thisbutton.position();
				for (var i = 0; i < choices.length; i++) {
					$("#droplist").append($("<div>").attr("class", "choice").html(choices[i]));
				}
				$("#droplist").css("position", "absolute");
				$("#droplist").css("background-color", "white");
				$("#droplist").css("border", "solid 1px black");
				$("#droplist").css("padding", "5px");
				$("#droplist").css("left", pos.left + 20);
				$("#droplist").css("top", pos.top + 20);
				$("#droplist").show();
				$("div.choice").css("cursor", "pointer");
				$("div.choice").click(function () {
					thisbutton.parents("table").find("tr").hide();
					thisbutton.parents("table").find("tr").eq(0).show();
					var kotoba = $(this);
					thisbutton.parents("table").find("td").each(function () {
						if ($(this).index() == thindex && $(this).html() == kotoba.html()) {
							$(this).parents("tr").show();
						}
					});
					$("#droplist").hide();
					thisbutton.val(markoff);
				});
			} else {
				thisbutton.parents("table").find("tr").show();
				thisbutton.val(markon);
			}
		});
	});
}
