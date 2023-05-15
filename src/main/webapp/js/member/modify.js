let checkEmail = true;
let checkNickName = true;
let checkPassword = true;

function enableSubmit() {
	if (checkEmail && checkNickName && checkPassword) {
		$("#modifyButton").removeAttr("disabled");
	} else {
		$("#modifyButton").attr("disabled", "");
	}
}

$("#inputEmail").keyup(function() {
	checkEmail = false;
	$("#availableEmailMessage").addClass("d-none");
	$("#notAvailableEmailMessage").addClass("d-none");
	enableSubmit();
});

$("#inputNickName").keyup(function() {
	checkNickName = false;
	$("#availableNickNameMessage").addClass("d-none");
	$("#notAvailableNickNameMessage").addClass("d-none");
	enableSubmit();
})

$("#checkNickNameBtn").click(function() {
	const usernickName = $("#inputNickName").val();

	$.ajax("/member/checkNickName/" + usernickName, {
		success: function(data) {

			if (data.available) {
				// 사용 가능하다는 메세지 출력
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");
				checkNickName = true;
			} else {
				// 사용 가능하지 않다는 메세지 출력
				$("#availableNickNameMessage").addClass("d-none");
				$("#notAvailableNickNameMessage").removeClass("d-none");
				checkNickName = false;
			}
		},
		complete: enableSubmit
	})
})

$("#checkEmailBtn").click(function() {
	const userEmail = $("#inputEmail").val();

	$.ajax("/member/checkEmail/" + userEmail, {
		success: function(data) {

			if (data.available) {
				// 사용 가능하다는 메세지 출력
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
				checkEmail = true;
			} else {
				// 사용 가능하지 않다는 메세지 출력
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
				checkEmail = false;
			}
		},
		complete: enableSubmit
	})
})

$("#inputPassword, #inputPasswordCheck").keyup(function() {
	const pw1 = $("#inputPassword").val();
	const pw2 = $("#inputPasswordCheck").val();

	if (pw1 == pw2) {
		$("#modifyButton").removeClass("disabled");
		$("#passwordSuccessText").removeClass("d-none");
		$("#passwordFailText").addClass("d-none");
		checkPassword = true;
	} else {
		$("#modifyButton").addClass("disabled");
		$("#passwordSuccessText").addClass("d-none");
		$("#passwordFailText").removeClass("d-none");
		checkPassword = false;
	}

	enableSubmit();
});