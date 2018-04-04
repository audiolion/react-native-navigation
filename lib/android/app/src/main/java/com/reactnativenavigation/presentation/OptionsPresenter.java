package com.reactnativenavigation.presentation;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.reactnativenavigation.interfaces.ChildDisappearListener;
import com.reactnativenavigation.parse.AnimationsOptions;
import com.reactnativenavigation.parse.Options;
import com.reactnativenavigation.parse.OrientationOptions;
import com.reactnativenavigation.parse.TopBarOptions;
import com.reactnativenavigation.parse.TopTabOptions;
import com.reactnativenavigation.parse.TopTabsOptions;
import com.reactnativenavigation.parse.params.Button;
import com.reactnativenavigation.viewcontrollers.IReactView;
import com.reactnativenavigation.views.Component;
import com.reactnativenavigation.views.topbar.TopBar;

import java.util.ArrayList;

public class OptionsPresenter {
    private TopBar topBar;

    public OptionsPresenter(TopBar topBar) {
        this.topBar = topBar;
    }

    public void applyChildOptions(Options options, Component child) {
        applyOrientation(options.orientationOptions);
        applyButtons(options.topBarOptions.leftButtons, options.topBarOptions.rightButtons);
        applyTopBarOptions(options.topBarOptions, options.animationsOptions, child);
        applyTopTabsOptions(options.topTabsOptions);
        applyTopTabOptions(options.topTabOptions);
        setCollapseOptions(options.topTabsOptions, options.topBarOptions, child);
    }

    public void applyOrientation(OrientationOptions options) {
        ((Activity) topBar.getContext()).setRequestedOrientation(options.getValue());
    }

    private void applyTopBarOptions(TopBarOptions options, AnimationsOptions animationOptions, Component component) {
        if (options.title.text.hasValue()) topBar.setTitle(options.title.text.get());
        if (options.title.component.hasValue())
            topBar.setTitleComponent(options.title.component.get(), options.title.alignment);
        if (options.title.color.hasValue()) topBar.setTitleTextColor(options.title.color.get());
        if (options.title.fontSize.hasValue())
            topBar.setTitleFontSize(options.title.fontSize.get());

        if (options.subtitle.text.hasValue()) topBar.setSubtitle(options.subtitle.text.get());
        if (options.subtitle.color.hasValue())
            topBar.setSubtitleColor(options.subtitle.color.get());
        if (options.subtitle.fontFamily != null)
            topBar.setSubtitleFontFamily(options.subtitle.fontFamily);
        if (options.subtitle.fontSize.hasValue())
            topBar.setTitleFontSize(options.subtitle.fontSize.get());

        topBar.setBackgroundColor(options.background.color);
        topBar.setBackgroundComponent(options.background.component);
        if (options.testId.hasValue()) topBar.setTestId(options.testId.get());

        topBar.setTitleTypeface(options.title.fontFamily);
        if (options.visible.isFalse()) {
            if (options.animate.isTrueOrUndefined()) {
                topBar.hideAnimate(animationOptions.pop.topBar);
            } else {
                topBar.hide();
            }
        }
        if (options.visible.isTrueOrUndefined()) {
            if (options.animate.isTrueOrUndefined()) {
                topBar.showAnimate(animationOptions.push.topBar);
            } else {
                topBar.show();
            }
        }
        if (options.drawBehind.isTrue()) {
            component.drawBehindTopBar();
        } else if (options.drawBehind.isFalseOrUndefined()) {
            component.drawBelowTopBar(topBar);
        }
    }

    private void applyButtons(ArrayList<Button> leftButtons, ArrayList<Button> rightButtons) {
        topBar.setLeftButtons(leftButtons);
        topBar.setRightButtons(rightButtons);
    }

    private void applyTopTabsOptions(TopTabsOptions options) {
        topBar.applyTopTabsColors(options.selectedTabColor, options.unselectedTabColor);
        topBar.applyTopTabsFontSize(options.fontSize);
        topBar.setTopTabsVisible(options.visible.isTrueOrUndefined());
    }

    private void applyTopTabOptions(TopTabOptions topTabOptions) {
        if (topTabOptions.fontFamily != null)
            topBar.setTopTabFontFamily(topTabOptions.tabIndex, topTabOptions.fontFamily);
    }

    private void setCollapseOptions(TopTabsOptions topTabsOptions, TopBarOptions topBarOptions, Component component) {
        if (topBarOptions.hideOnScroll.isTrue()) {
            if (topTabsOptions.hideOnScroll.isTrueOrUndefined()) {
                topBar.enableCollapse(((IReactView) component).getScrollEventListener());
            }
            if (topTabsOptions.hideOnScroll.isFalse()) {
                topBar.disableTopTabsCollapse();
                topBar.enableTitleBarCollapse(((IReactView) component).getScrollEventListener());
            }
        }
        if (topBarOptions.hideOnScroll.isFalseOrUndefined()) {
            if (topTabsOptions.hideOnScroll.isFalseOrUndefined()) {
                topBar.disableTopTabsCollapse();
                topBar.disableTitleBarCollapse();
                topBar.disableCollapse();
            }
            if (topTabsOptions.hideOnScroll.isTrue()) {
                topBar.disableTitleBarCollapse();
                topBar.disableCollapse();
                topBar.enableTopTabsCollapse(((IReactView) component).getScrollEventListener());
            }
        }
    }

    public void onChildWillDisappear(Options disappearing, Options appearing, @NonNull ChildDisappearListener childDisappearListener) {
        if (disappearing.topBarOptions.visible.isTrueOrUndefined() && appearing.topBarOptions.visible.isFalse()) {
            if (disappearing.topBarOptions.animate.isTrueOrUndefined()) {
                topBar.hideAnimate(disappearing.animationsOptions.pop.topBar, childDisappearListener::childDisappear);
            } else {
                topBar.hide();
                childDisappearListener.childDisappear();
            }
        } else {
            childDisappearListener.childDisappear();
        }
    }

    public void mergeChildOptions(Options options, Component child) {
        mergeOrientation(options.orientationOptions);
        mergeButtons(options.topBarOptions.leftButtons, options.topBarOptions.rightButtons);
        mergeTopBarOptions(options.topBarOptions, options.animationsOptions, child);
        mergeTopTabsOptions(options.topTabsOptions);
        mergeTopTabOptions(options.topTabOptions);
        setCollapseOptions(options.topTabsOptions, options.topBarOptions, child);
    }

    private void mergeOrientation(OrientationOptions orientationOptions) {
        if (orientationOptions.hasValue()) applyOrientation(orientationOptions);
    }

    private void mergeButtons(ArrayList<Button> leftButtons, ArrayList<Button> rightButtons) {
        if (leftButtons != null) topBar.setLeftButtons(leftButtons);
        if (rightButtons != null) topBar.setRightButtons(rightButtons);
    }

    private void mergeTopBarOptions(TopBarOptions options, AnimationsOptions animationsOptions, Component component) {
        if (options.title.text.hasValue()) topBar.setTitle(options.title.text.get());
        if (options.title.component.hasValue())
            topBar.setTitleComponent(options.title.component.get(), options.title.alignment);
        if (options.title.color.hasValue()) topBar.setTitleTextColor(options.title.color.get());
        if (options.title.fontSize.hasValue())
            topBar.setTitleFontSize(options.title.fontSize.get());

        if (options.subtitle.text.hasValue()) topBar.setSubtitle(options.subtitle.text.get());
        if (options.subtitle.color.hasValue())
            topBar.setSubtitleColor(options.subtitle.color.get());

        if (options.background.color.hasValue())
            topBar.setBackgroundColor(options.background.color);

        if (options.testId.hasValue()) topBar.setTestId(options.testId.get());

        if (options.title.fontFamily != null) topBar.setTitleTypeface(options.title.fontFamily);
        if (options.visible.isFalse()) {
            if (options.animate.isTrueOrUndefined()) {
                topBar.hideAnimate(animationsOptions.pop.topBar);
            } else {
                topBar.hide();
            }
        }
        if (options.visible.isTrue()) {
            if (options.animate.isTrueOrUndefined()) {
                topBar.showAnimate(animationsOptions.push.topBar);
            } else {
                topBar.show();
            }
        }
        if (options.drawBehind.isTrue()) {
            component.drawBehindTopBar();
        }
        if (options.drawBehind.isFalse()) {
            component.drawBelowTopBar(topBar);
        }
    }

    private void mergeTopTabsOptions(TopTabsOptions options) {
        if (options.selectedTabColor.hasValue() && options.unselectedTabColor.hasValue())
            topBar.applyTopTabsColors(options.selectedTabColor, options.unselectedTabColor);
        if (options.fontSize.hasValue()) topBar.applyTopTabsFontSize(options.fontSize);
        if (options.visible.hasValue()) topBar.setTopTabsVisible(options.visible.isTrue());
    }

    private void mergeTopTabOptions(TopTabOptions topTabOptions) {
        if (topTabOptions.fontFamily != null)
            topBar.setTopTabFontFamily(topTabOptions.tabIndex, topTabOptions.fontFamily);
    }
}
