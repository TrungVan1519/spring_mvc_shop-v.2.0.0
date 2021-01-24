<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Footer -->
<footer class="page-footer font-small mdb-color lighten-3 pt-5">

	<!-- Footer images -->
	<div class="container-fluid">
		<div class="row">

			<c:forEach var="i" begin="1" end="6" step="1">
				<div class="col-lg-2 col-md-2 col-sm-4 pt-3">
					<div class="view overlay pointer-cursor z-depth-1-half">
						<a href="<c:url value="/public/img/app/fall-guys-${i}.jpg"/>"
							data-fancybox="footer-img-gallery" data-caption="footer img">
							<img src="<c:url value="/public/img/app/fall-guys-${i}.jpg"/>"
								class="img-fluid" alt="Footer image" />
							<div class="mask rgba-white-light"></div>
						</a>
					</div>
				</div>
			</c:forEach>

		</div>
	</div>
	<!-- Footer images -->

	<!-- Copyright -->
	<div class="footer-copyright text-center py-3">
		© 2020 Copyright:
		<a class="copyright" href="<c:url value="/"/>"> gBean.com</a>
	</div>
	<!-- Copyright -->

</footer>
<!-- Footer -->

<script>
	document.addEventListener("DOMContentLoaded", function() {
		const view = $(".view");
		const footerImgGallery = $("[data-fancybox='footer-img-gallery']");
		view.hover(function(e) {
			const imgFluid = $(this).children().children(".img-fluid");
			gsap.timeline().to(imgFluid, {
				duration : 0.5,
				scale : 1.1,
				opacity : 0.8
			});
		}, function(e) {
			const imgFluid = $(this).children().children(".img-fluid");
			gsap.timeline().to(imgFluid, {
				duration : 0.5,
				scale : 1,
				opacity : 1
			});
		});
		footerImgGallery.fancybox({
			loop : true,
			gutter : 50,
			preventCaptionOverlap : true,
			arrows : true,
			infobar : true,
			toolbar : true,
			buttons : [ "zoom", "share", "slideShow", "fullScreen", "download",
					"thumbs", "close", ],
			protect : true,
			defaultType : "image",
			animationEffect : "zoom",
			animationDuration : 366,
			zoomOpacity : "auto",
			transitionEffect : "tube",
			transitionDuration : 366,
			fullScreen : {
				autoStart : false,
			},
			touch : {
				vertical : true, // Allow to drag content vertically
				momentum : true, // Continue movement after releasing mouse/touch when panning
			},
			slideShow : {
				autoStart : false,
				speed : 2000,
			},
			thumbs : {
				autoStart : true,
			},
			wheel : "auto",
		});
	});
</script>