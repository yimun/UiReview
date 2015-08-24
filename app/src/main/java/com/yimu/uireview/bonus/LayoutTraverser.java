package com.yimu.uireview.bonus;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linwei on 15-8-18.
 */
public class LayoutTraverser {
    public interface Processor {
        void process(View view);
    }

    private final Processor processor;

    private LayoutTraverser(Processor processor) {
        this.processor = processor;
    }

    public static LayoutTraverser build(Processor processor) {
        return new LayoutTraverser(processor);
    }

    public void traverse(ViewGroup root) {
        final int childCount = root.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            final View child = root.getChildAt(i);
            processor.process(child);

            if (child instanceof ViewGroup) {
                traverse((ViewGroup) child);
            }
        }
    }
}