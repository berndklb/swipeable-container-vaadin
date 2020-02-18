package com.github.berndklb.vaadin;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

@Tag("swipeable-container")
@JsModule("./src/swipeable-container.js")
public class SwipeableContainer extends Component {

    private List<SwipeEventListener> swipeListener = new ArrayList<>();

    public SwipeableContainer() {

        getElement().addEventListener("swipe-right", e -> {
            System.out.println("swipe-right: " + e);
            if (!swipeListener.isEmpty()) {
                swipeListener.parallelStream()
                    .forEach(ev -> ev.swipe(new SwipeEvent(SwipeDirection.RIGHT)));
            }
        });
        getElement().addEventListener("swipe-left", e -> {
            System.out.println("swipe-left: " + e);
            if (!swipeListener.isEmpty()) {
                swipeListener.parallelStream()
                    .forEach(ev -> ev.swipe(new SwipeEvent(SwipeDirection.LEFT)));
            }
        });
    }

    public void addSwipeEventListener(SwipeEventListener listener) {
        this.swipeListener.add(listener);
    }

    public void setContent(Component comp) {
        getElement().removeAllChildren();
        getElement().appendChild(comp.getElement());
    }

    @FunctionalInterface
    public interface SwipeEventListener {
        void swipe(SwipeEvent event);
    }

    public class SwipeEvent {
        private SwipeDirection direction;

        public SwipeEvent(SwipeDirection direction) {
            this.direction = direction;
        }

        public SwipeDirection getDirection() {
            return direction;
        }

        public void setDirection(SwipeDirection direction) {
            this.direction = direction;
        }
    }

    public enum SwipeDirection {
        LEFT, RIGHT;
    }
}
