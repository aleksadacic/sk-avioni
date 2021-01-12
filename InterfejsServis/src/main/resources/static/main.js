$('#myTable tbody tr').click(function() {
    $(this).addClass('active').siblings().removeClass('active');
    $(this).addClass('bg-success').siblings().removeClass('bg-success');
    let x = $(this).find("td")[0];
    $("#selected-flight").val(x.textContent);
    $("#flightid").val($(this).find("td")[4].textContent);
    let price = $(this).find("td")[3].textContent;
    let disc = $("#discount").text();
    let wdisc = parseFloat(price) - (parseFloat(price) * parseInt(disc) / 100);
    $("#flight-price").val(wdisc.toFixed(2));
    $("#cardid").val($("#selectcard").val());
});

$('#loginbutton').click(function() {
	if ($('#email').val().length <= 0 || $('#password').val().length <= 0) {
        alert("You have to fill all the fields!")
    }
    else {
    	$('#login-form').submit();
    }
});

$("#selectcard").change(function(){

    $("#cardid").val($(this).val());

}); 