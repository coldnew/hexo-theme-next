<!doctype html>
<html class="theme-next {% if theme.use_motion %}use-motion{% endif %} {% if theme.scheme %}theme-next-{{ theme.scheme | lower }}{% endif %}">
  <head>
  {% include '_partials/head.swig' %}
  <title>{% block title %}{% endblock %}</title>
</head>

<body itemscope itemtype="https://schema.org/WebPage" lang="{{ config.language }}">

  {% include '_partials/old-browsers.swig' %}
  {% include '_scripts/analytics.swig' %}

  <div class="container-fluid one-column {% block page_class %}{% endblock %}">
    <div class="headband"></div>

    <header id="header" class="header" itemscope itemtype="https://schema.org/WPHeader">
      <div class="header-inner"> {%- include '_partials/header.swig' %} </div>
    </header>

    <main id="main" class="main">
      <div class="main-inner">
        <div id="content" class="content"> {% block content %}{% endblock %} </div>

        {% if page.comments %}
          <div class="comments" id="comments">
            {% if theme.gitalk.enable %}
               <div id="gitalk-container"></div>
            {% endif %}
          </div>
        {% endif %}
      </div>

      {% block sidebar %}{% endblock %}
    </main>

    <footer id="footer" class="container">
      <div class="footer">
        <div class="footer-inner"> {% include '_partials/footer.swig' %} </div>
      </div>
    </footer>

    <div class="back-to-top"></div>
  </div>

  <!-- <script type="text/javascript" src="{{ url_for(theme.vendors) }}/jquery/dist/jquery.min.js"></script> -->
  <!-- <link rel="stylesheet" href="{{ url_for(theme.vendors) }}/bootstrap/dist/css/bootstrap.min.css" type="text/css"> -->
  <!-- <script type="text/javascript" src="{{ url_for(theme.vendors) }}/bootstrap/dist/js/bootstrap.min.js"></script> -->

  {# This block is used to set options before any other scripts run #}
  {% block script_extra_before %}{% endblock %}
  {% block comment_system %}
    {% include '_scripts/comments/duoshuo.swig' %}
  {% endblock %}
  {% include '_scripts/fancy-box.swig' %}
  {% include '_scripts/helpers.swig' %}
  {% include '_scripts/motion.swig' %}

  <script type="text/javascript" src="{{ url_for(theme.js) }}/nav-toggle.js?v={{ theme.version }}"></script>
  <script type="text/javascript" src="{{ url_for(theme.vendors) }}/fastclick/lib/fastclick.min.js?v=1.0.6"></script>

  {% block script_extra %}{% endblock %}

  <script type="text/javascript">
    $(document).ready(function () {
      if (CONFIG.sidebar === 'always') {
        displaySidebar();
      }
      if (isMobile()) {
        FastClick.attach(document.body);
      }
    });
  </script>

  {% include '_scripts/mathjax.swig' %}
  {% block comment_system %}{% endblock %}
  {% include '_scripts/baidushare.swig' %}

  {# LazyLoad #}
  <script type="text/javascript" src="{{ url_for(theme.js) }}/lazyload.js"></script>
  <script type="text/javascript">
    $(function () {
      $("#posts").find('img').lazyload({
        placeholder: "{{ url_for(theme.images) }}/loading.gif",
        effect: "fadeIn"
      });
    });
  </script>
  <script type="text/javascript" src="{{ url_for(theme.js) }}/app.js"></script>
{% include '_partials/gitalk.swig' %}
</body>
</html>
