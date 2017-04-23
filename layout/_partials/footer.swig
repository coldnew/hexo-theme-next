
<div class="copyright" >
  {% set current = date(Date.now(), "YYYY") %}
  {{ "Copyright " }} &copy; &nbsp; {% if theme.since and theme.since != current %} {{ theme.since }} - {% endif %}
  <span itemprop="copyrightYear">{{ current }}</span>
  <span class="author" itemprop="copyrightHolder">{{ config.author }}</span>
 {{ " " }} &middot; {{ " Powered by " }} <a href="https://github.com/coldnew/hexo-renderer-org" target="_blank">coldnew/hexo-renderer-org</a>
</div>

<!--
<div class="powered-by">
  {{ __('footer.powered', '<a class="theme-link" href="http://hexo.io">Hexo</a>') }}
</div>

<div class="theme-info">
  {{ __('footer.theme') }} -
  <a class="theme-link" href="https://github.com/iissnan/hexo-theme-next">
    NexT{% if theme.scheme %}.{{ theme.scheme }}{% endif %}
  </a>
</div>
-->

{% block footer %}{% endblock %}
