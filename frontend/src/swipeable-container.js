import { LitElement, html } from 'lit-element';
import {GestureEventListeners} from '@polymer/polymer/lib/mixins/gesture-event-listeners';
import * as Gestures from '@polymer/polymer/lib/utils/gestures';

class SwipeableContainer extends GestureEventListeners(LitElement) {
  
  render() {
    return html`
    <div style="display:flex; position:relative" class="container">
    	<slot></slot>
    </div>`;
  }
 
  constructor() {
	super();
	Gestures.addListener(this, "track", this.handleTrack.bind(this));
  }
    
  handleTrack(e) {
	  switch(e.detail.state) {
	  case "track":
		  console.log("track" + e.detail.ddx);
		  console.log(e);
		  console.log(e.path.length);
		  if(e.path.length >= 2 && (e.detail.dx > 10 || e.detail.dx < -10)) {
		  	console.log(e.path[1]);
		  	e.path[1].style.position = 'relative';
		  	var newPosition = (0 + e.detail.dx);
		  	console.log(newPosition);
		  	e.path[1].style.left = newPosition + 'px';
		  }
		  if(e.detail.ddx > 10) {
			  console.log("swipe-right");
			  this.dispatchEvent(new CustomEvent('swipe-right', {bubbles: true, composed: true}));
		  }
		  if(e.detail.ddx < -10) {
			  console.log("swipe-left");
			  this.dispatchEvent(new CustomEvent('swipe-left', {bubbles: true, composed: true}));
		  }
		  break;
	  case "start":
		  console.log("start" + e.detail.ddx);
		  break;
	  case "end":
		  console.log("end" + e.detail.ddx);
		  e.path[1].style.left = 0 + "px";
		  break;
	  }
  }
  
}
customElements.define('swipeable-container', SwipeableContainer);