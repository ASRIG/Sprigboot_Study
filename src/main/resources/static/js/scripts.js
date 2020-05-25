
$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	console.log("click me");
	e.preventDefault();

	// 해당하는 태그에 있는 데이터를 읽어옴.
	let queryString = $(".answer-write").serialize();
	console.log("query :: " + queryString);

	let url = $(".answer-write").attr("action");
	console.log("url :: " + url);

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		// 실패했을 경우 onError 호출
		error : onError,
		// 성공했을 때 onSuccess 호출
		success : onSuccess
	});

	function onError() {
		
	}

	// data는 Answer가 넘어옴
	function onSuccess(data, status) {
		console.log(data);
		var answerTemplate = $("#answerTemplate").html();
		let template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
		// prepend, append를 이용해 앞 혹은 뒤에 값을 넣어줌.
		$(".qna-comment-slipp-articles").append(template);
		
		// 텍스트 (답글) 입력창 초기화
		$(".answer-write textarea[name=contents]").val("");
	}
	
	// 템플릿을 쓰기위해 펼요
	String.prototype.format = function() {
		var args = arguments;
		return this.replace(/{(\d+)}/g, function(match, number) {
			return typeof args[number] != 'undefined' ? args[number] : match;
		});
	};
}

//$(".link-delete-article").click(deleteAnswer);
$(document).on('click', '.link-delete-article', deleteAnswer);

function deleteAnswer(e){
	e.preventDefault();
	// $(this)는  클릭한 자기 자신에 대한 것.
	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("url :: " + url);
	
	$.ajax({
		type: 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status){
			console.log("error");
		},
		success : function(data, status){
			console.log(data);
			if(data.valid){
				// 자기랑 가장 가까운 article 찾아서 리무브
				deleteBtn.closest("article").remove();
			}
		}
	});
}










