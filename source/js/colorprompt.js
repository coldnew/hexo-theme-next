// Color Bash prompt in example block
$(document).ready(function () {
  'use strict';

  // prevent selectable in shell prompt
  function span (x) {
    var pre = '<span onmousedown=\"return false;\" onselectstart=\"return false;\">';
    var pos = '</span>';
    // extra is to make commandline can easy copy without copy the prompt
    var extra = '<span style="width: 0; height: 0; display: inline-block; overflow: hidden;"><span style="display: block;"></span></span>';
    return pre + x + pos + extra;
  }

  var userHighlight  = span ('<font color=\"lightgreen\">$1</font><font color=\"lightblue\">$2</font>');
  var rootHighlight  = span ('<font color=\"crimson\">$1</font><font color=\"lightblue\">$2</font>');

  // https://regex101.com/#javascript
  function color_shell_prompt(className) {
    var block = document.getElementsByClassName(className);
    for(var i = 0, l = block.length; i < l; i++) {
      // highlight `user@hostname directory $'
      block[i].innerHTML = block[i].innerHTML.replace(/^(\w*@\w*)(\s*[:~](.+)\/([^/]+)[$]\s)/, userHighlight);
      // highlight `user@hostname ~ $'
      block[i].innerHTML = block[i].innerHTML.replace(/^(\w*@\w*)(\s*[:~](.*)([^/]+)[$]\s)/, userHighlight);
      // highlight `root@hostname #'
      block[i].innerHTML = block[i].innerHTML.replace(/^(root@\w*)(\s*[:~](.+)\/([^/]+)[#])/, rootHighlight);
      // highlight `hostname #'
      block[i].innerHTML = block[i].innerHTML.replace(/^(\w*)(\s*[:~](.+)\/([^/]+)[#])/, rootHighlight);
      // highlight `hostname directory #' (Gentoo Linux root)
      block[i].innerHTML = block[i].innerHTML.replace(/^(\w*)(\s*\w* [#])/, rootHighlight);
    }
  }

  // color some class with shellprompt
  color_shell_prompt('example');
  color_shell_prompt('src src-sh');
});
