/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.takisoft.colorpicker;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.takisoft.colorpicker.ColorPickerSwatch.OnColorSelectedListener;

/**
 * A color picker custom view which creates an grid of color squares.  The number of squares per
 * row (and the padding between the squares) is determined by the user.
 */
public class ColorPickerPaletteFlex2 extends FlexboxLayout implements OnColorSelectedListener {
    public OnColorSelectedListener mOnColorSelectedListener;

    private String mDescription;
    private String mDescriptionSelected;

    public ColorPickerPaletteFlex2(Context context) {
        this(context, null);
    }

    public ColorPickerPaletteFlex2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickerPaletteFlex2(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Resources res = getResources();

        mDescription = res.getString(R.string.color_swatch_description);
        mDescriptionSelected = res.getString(R.string.color_swatch_description_selected);

        setFlexWrap(FlexWrap.WRAP);
        setFlexDirection(FlexDirection.ROW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setup(ColorPickerDialog.Params params) {
        this.removeAllViews();

        for (int i = 0; i < params.mColors.length; i++) {
            ColorPickerSwatch view = createItem(params, i);
            addView(view);
        }
    }

    private ColorPickerSwatch createItem(ColorPickerDialog.Params params, int position) {
        ColorPickerSwatch view = new ColorPickerSwatch(getContext());
        view.setOnColorSelectedListener(this);

        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(params.mSwatchLength, params.mSwatchLength);
        layoutParams.setMargins(params.mMarginSize, params.mMarginSize, params.mMarginSize, params.mMarginSize);
        layoutParams.setFlexGrow(0);
        layoutParams.setFlexShrink(0);

        if (params.mColumns > 0 && position % params.mColumns == 0) {
            layoutParams.setWrapBefore(true);
        } else {
            layoutParams.setWrapBefore(false);
        }

        view.setLayoutParams(layoutParams);

        boolean selected = params.mSelectedColor == params.mColors[position];

        view.setColor(params.mColors[position]);
        view.setChecked(selected);

        setSwatchDescription(position, selected, view, params.mColorContentDescriptions);

        return view;
    }

    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.mOnColorSelectedListener = listener;
    }

    @Override
    public void onColorSelected(int color) {
        if (mOnColorSelectedListener != null) {
            mOnColorSelectedListener.onColorSelected(color);
        }
    }

    private void setSwatchDescription(int index, boolean selected, View swatch, CharSequence[] contentDescriptions) {
        CharSequence description;
        if (contentDescriptions != null && contentDescriptions.length > index) {
            description = contentDescriptions[index];
        } else {
            int accessibilityIndex = index + 1;

            if (selected) {
                description = String.format(mDescriptionSelected, accessibilityIndex);
            } else {
                description = String.format(mDescription, accessibilityIndex);
            }
        }
        swatch.setContentDescription(description);
    }
}
