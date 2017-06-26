<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Register</title>
<sj:head jquerytheme="start" jqueryui="true" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h3>Request a new institution or branch</h3>

		<div class="panel panel-default">
			<div class="panel-body">
				Please enter the institution as "Partner," the legal entity.<br>
				The name of the institution should be in its official language.
				(e.g. For CIAT: Centro Internacional de Agricultura Tropical)
			</div>
		</div>

		<h4>
			Is this institution a branch?
			<button type="button" class="btn btn-default" id="buttonOK">Yes</button>
			<button type="button" class="btn btn-default" id="buttonKO">No</button>
			<span id="msg_branch" style="display: none;"
				class="label label-warning">Indicate if it is a branch or no</span>
		</h4>

		<s:form id="request" name="request" action="saverequest">
			<div class="form-group" id="div_institution">
				<label for="institution">Select institution headquarter:*</label> <select
					class="form-control" id="institution"
					name="requestBean.institution">
					<option value="0">Select an options...</option>
				</select> <span id="msg_institution" style="display: none;"
					class="label label-warning">Select one institution</span>
			</div>
			<input type="hidden" id="headquarter" name="requestBean.headquarter">
			<div class="form-group">
				<label for="acronym">Acronym:</label>
				<div class="input-group">
					<input type="text" class="form-control" id="acronym"
						name="requestBean.acronym" placeholder="Acronym"> <span
						class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span>
				</div>
				<span id="msg_acronym" style="display: none;"
					class="label label-warning">It must be less than 10
					characters</span>
			</div>
			<div class="form-group">
				<label for="name">Name:*</label> <input type="text"
					class="form-control" id="name" name="requestBean.name"
					placeholder="Name"> <span id="msg_name"
					style="display: none;" class="label label-warning">It must
					be less than 10 words</span>
			</div>
			<div class="form-group">
				<label for="type">Type:*</label> <select class="form-control"
					id="type" name="requestBean.type">
					<option value="0">Select an options...</option>
					<option value="1">Academic Institutions</option>
					<option value="2">Donor</option>
					<option value="3">Non-Governmental Organization</option>
					<option value="4">Research Institution</option>
				</select> <span id="msg_type" style="display: none;"
					class="label label-warning">Select one type</span>
			</div>
			<div class="form-group">
				<label for="country">Country:*</label> <select class="form-control"
					id="country" name="requestBean.country">
					<option value="0">Select an options...</option>
				</select> <span id="msg_country" style="display: none;"
					class="label label-warning">Select one country</span>
			</div>
			<div class="form-group">
				<label for="city">City:*</label> <input type="text"
					class="form-control" id="city" name="requestBean.city"
					placeholder="City"> <span id="msg_city"
					style="display: none;" class="label label-warning">Indicate
					one city</span>
			</div>
			<div class="form-group">
				<label for="website">If you know the partner website please
					paste the link below:</label> <input type="text" class="form-control"
					id="website" name="requestBean.website" placeholder="Website">
				<span id="msg_website" style="display: none;"
					class="label label-warning">The URL must starts with http or
					https</span>
			</div>
			<button type="submit" class="btn btn-info">
				<span class="glyphicon glyphicon-send" aria-hidden="true"></span>
				Request add new partner
			</button>
		</s:form>

	</div>

	<script type="text/javascript">
		$(function() {

			$.getJSON('listrequest', {
				typeRequest : "LIST_REQUEST_NAME"
			}, function(jsonResponse) {
				var select = $('#institution');
				select.find('option').remove();
				$.each(jsonResponse.mapInstitutions, function(key, value) {
					$('<option>').val(key).text(value).appendTo(select);
				});
			});

			$.getJSON('listcountries', {}, function(jsonResponse) {
				var select = $('#country');
				select.find('option').remove();
				$.each(jsonResponse.mapCountries, function(key, value) {
					$('<option>').val(value).text(value).appendTo(select);
				});
			});

			$("#buttonOK").click(
					function(event) {
						event.preventDefault();

						// to apply css styles
						$("#buttonOK").removeClass("btn-default").addClass(
								"btn-success");
						$("#buttonKO").removeClass("btn-success").addClass(
								"btn-default");

						// if the institution is a branch, to show the field to select institution headquarter
						$("#div_institution").show();

						// To mark the institution as a branch
						$("#headquarter").val("No");

						// To hide error message for the branch
						$("#msg_branch").hide();
					});

			$("#buttonKO").click(
					function(event) {
						event.preventDefault();

						// Apply css styles to buttons
						$("#buttonKO").removeClass("btn-default").addClass(
								"btn-success");
						$("#buttonOK").removeClass("btn-success").addClass(
								"btn-default");

						// if the institution isn't a branch, to hide the field that select institution headquarter
						$("#div_institution").hide();

						// Mark the institution as a headquarter
						$("#headquarter").val("Yes");

						// Hide error message for the branch
						$("#msg_branch").hide();
					});

			$("#request").submit(function(event) {

				if (!validateHeadquarter()) {
					return false;
				}

				var headquarter = $("#headquarter").val();
				if (headquarter == "No") {
					if (!validateInstitution()) {
						return false;
					}
				}

				if (!validateAcronym()) {
					return false;
				}

				if (!validateAcronym()) {
					return false;
				}

				if (!validateName()) {
					return false;
				}

				if (!validateType()) {
					return false;
				}

				if (!validateCountry()) {
					return false;
				}

				if (!validateCity()) {
					return false;
				}

				if (!validateURL()) {
					return false;
				}

				event.preventDefault();

				console.log("Paso");
				this.submit();
			});

			$("#institution").change(function() {
				validateInstitution();
			});

			$("#acronym").change(function() {
				validateAcronym();
			});

			$("#name").change(function() {
				validateName();
			});

			$("#type").change(function() {
				validateType();
			});

			$("#country").change(function() {
				validateCountry();
			});

			$("#city").change(function() {
				validateCity();
			});

			$("#website").change(function() {
				validateURL();
			});

			function validateHeadquarter() {
				// Get the value of headquarter field
				var headquarter = $("#headquarter").val();
				// Validate the headquarter field
				if (headquarter.length == 0) {
					$("#msg_branch").show();
					return false;
				} else {
					$("#msg_branch").hide();
					return true;
				}
			}

			function validateInstitution() {
				// Get the value of institution field
				var institution = $("#institution").val();
				// Validate the institution field
				if (institution == "0") {
					$("#msg_institution").show();
					return false;
				} else {
					$("#msg_institution").hide();
					return true;
				}
			}

			function validateAcronym() {
				// Get the value of acronym field
				var acronym = $.trim($("#acronym").val());
				// Validate the acronym field
				if (acronym.length > 10) {
					$("#msg_acronym").show();
					return false;
				} else {
					$("#msg_acronym").hide();
					return true;
				}
			}

			function validateName() {
				// Get the value of name field
				var name = $.trim($("#name").val());
				// Validate the name field
				if (name.length == 0 || name.split(' ').length > 10) {
					$("#msg_name").show();
					return false;
				} else {
					$("#msg_name").hide();
					return true;
				}
			}

			function validateType() {
				// Get the value of type field
				var type = $("#type").val();
				// Validate the type field
				if (type == "0") {
					$("#msg_type").show();
					return false;
				} else {
					$("#msg_type").hide();
					return true;
				}
			}

			function validateCountry() {
				// Get the value of country field
				var country = $("#country").val();
				// Validate the country field
				if (country == "0") {
					$("#msg_country").show();
					return false;
				} else {
					$("#msg_country").hide();
					return true;
				}
			}

			function validateCity() {
				// Get the value of city field
				var city = $("#city").val();
				// Validate the city field
				if (city.length == 0) {
					$("#msg_city").show();
					return false;
				} else {
					$("#msg_city").hide();
					return true;
				}
			}

			function validateURL() {
				// Get the value of website field
				var website = $("#website").val();
				// Validate the website field
				if (website.length > 0) {
					if (!(website.indexOf('http') == 0 || website
							.indexOf('https') == 0)) {
						$("#msg_website").show();
						return false;
					} else {
						$("#msg_website").hide();
					}
				}
				return true;
			}
		});
	</script>
</body>
</html>