/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
  var loginPanel;
  loginPanel = $('div#login-panel');
  loginPanel.hide();
  
  var height;
  height = loginPanel.height();
  
  loginPanel.css({
    'margin-top' : -height/2,
    'position' : 'absolute',
    'top' : '50%'});
  loginPanel.fadeIn('slow');
});