// http://milanaryal.com/2015/implementing-responsive-images-videos-and-tables-with-bootstrap/
$(document).ready(function () {

  // For embed YouTube videos
  $('iframe[src*="youtube.com"]').wrap('<div class="embed-responsive embed-responsive-16by9"></div>');
  $('iframe[src*="youtube.com"]').addClass('embed-responsive-item');

  // For embed Vimeo videos
  $('iframe[src*="vimeo.com"]').wrap('<div class="embed-responsive embed-responsive-16by9"></div>');
  $('iframe[src*="vimeo.com"]').addClass('embed-responsive-item');

  // For SlideShare slides
  $('iframe[src*="slideshare.net"]').wrap('<div class="embed-responsive embed-responsive-16by9"></div>');
  $('iframe[src*="slideshare.net"]').addClass('embed-responsive-item');

});
