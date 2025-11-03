package com.example.my_profile;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.ImageViewCompat;
import android.content.res.ColorStateList;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private RelativeLayout mainLayout;
    private SwitchCompat themeSwitch;
    private LinearLayout welcomeContainer, homeContainer;
    private ImageView profileImage, glowEffect, imageShadow;
    private TextView nameText, subtitleText;
    private LinearLayout homeContent, aboutContent, profileContent;
    private LinearLayout navHome, navAbout, navProfile;
    private ImageView navHomeIcon, navAboutIcon, navProfileIcon;
    private TextView navHomeText, navAboutText, navProfileText;

    // Animation and Theme
    private boolean isDarkMode = false;
    private Handler handler;
    private AnimatorSet welcomeAnimations;

    // Colors - Made final and properly initialized
    private static final int[] LIGHT_GRADIENT_COLORS = {0xFF667eea, 0xFF764ba2};
    private static final int[] DARK_GRADIENT_COLORS = {0xFF2C3E50, 0xFF34495E};
    private static final int LIGHT_PRIMARY_TEXT = 0xFF2C3E50;
    private static final int DARK_PRIMARY_TEXT = 0xFFFFFFFF;
    private static final int LIGHT_SECONDARY_TEXT = 0xFF7F8C8D;
    private static final int DARK_SECONDARY_TEXT = 0xFFBDC3C7;
    private static final int ACCENT_COLOR = 0xFF667eea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize handler with main looper
        handler = new Handler(Looper.getMainLooper());

        initializeViews();
        loadThemePreference();
        setupThemeToggle();
        startWelcomeAnimations();
        setupBottomNavigation();

        // Auto transition to home after 20 seconds
        handler.postDelayed(this::transitionToHome, 20000);
    }

    private void initializeViews() {
        mainLayout = findViewById(R.id.main_layout);
        themeSwitch = findViewById(R.id.theme_switch);
        welcomeContainer = findViewById(R.id.welcome_container);
        homeContainer = findViewById(R.id.home_container);
        profileImage = findViewById(R.id.profile_image);
        glowEffect = findViewById(R.id.glow_effect);
        imageShadow = findViewById(R.id.image_shadow);
        nameText = findViewById(R.id.name_text);
        subtitleText = findViewById(R.id.subtitle_text);

        // Home screen components
        homeContent = findViewById(R.id.home_content);
        aboutContent = findViewById(R.id.about_content);
        profileContent = findViewById(R.id.profile_content);

        // Bottom navigation
        navHome = findViewById(R.id.nav_home);
        navAbout = findViewById(R.id.nav_about);
        navProfile = findViewById(R.id.nav_profile);
        navHomeIcon = findViewById(R.id.nav_home_icon);
        navAboutIcon = findViewById(R.id.nav_about_icon);
        navProfileIcon = findViewById(R.id.nav_profile_icon);
        navHomeText = findViewById(R.id.nav_home_text);
        navAboutText = findViewById(R.id.nav_about_text);
        navProfileText = findViewById(R.id.nav_profile_text);
    }

    private void loadThemePreference() {
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        isDarkMode = prefs.getBoolean("isDarkMode", false);
        themeSwitch.setChecked(isDarkMode);
        applyTheme();
    }

    private void setupThemeToggle() {
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDarkMode = isChecked;
            applyTheme();
            saveThemePreference();
            animateThemeTransition();
        });
    }

    private void saveThemePreference() {
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        prefs.edit().putBoolean("isDarkMode", isDarkMode).apply();
    }

    private void applyTheme() {
        // Apply gradient background
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                isDarkMode ? DARK_GRADIENT_COLORS : LIGHT_GRADIENT_COLORS
        );
        mainLayout.setBackground(gradient);

        // Apply text colors
        int primaryTextColor = isDarkMode ? DARK_PRIMARY_TEXT : LIGHT_PRIMARY_TEXT;
        int secondaryTextColor = isDarkMode ? DARK_SECONDARY_TEXT : LIGHT_SECONDARY_TEXT;

        nameText.setTextColor(primaryTextColor);
        subtitleText.setTextColor(secondaryTextColor);
        themeSwitch.setTextColor(primaryTextColor);

        // Update all text views in home screen
        updateHomeScreenColors(primaryTextColor, secondaryTextColor);
    }

    private void updateHomeScreenColors(int primaryColor, int secondaryColor) {
        TextView welcomeTitle = findViewById(R.id.welcome_title);
        TextView welcomeSubtitle = findViewById(R.id.welcome_subtitle);

        if (welcomeTitle != null) welcomeTitle.setTextColor(primaryColor);
        if (welcomeSubtitle != null) welcomeSubtitle.setTextColor(secondaryColor);

        // Update navigation text colors
        updateNavigationColors();
    }

    private void animateThemeTransition() {
        ValueAnimator colorAnimation = ValueAnimator.ofFloat(0f, 1f);
        colorAnimation.setDuration(500);
        colorAnimation.setInterpolator(new DecelerateInterpolator());
        colorAnimation.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            mainLayout.setAlpha(0.8f + (0.2f * fraction));
        });
        colorAnimation.start();
    }

    private void startWelcomeAnimations() {
        // Reset initial states
        profileImage.setScaleX(0f);
        profileImage.setScaleY(0f);
        profileImage.setRotation(0f);
        nameText.setTranslationY(100f);
        nameText.setAlpha(0f);
        subtitleText.setTranslationY(50f);
        subtitleText.setAlpha(0f);
        glowEffect.setAlpha(0f);
        imageShadow.setAlpha(0f);

        welcomeAnimations = new AnimatorSet();

        // Image scale and rotation animation with 3D effect
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(profileImage, "scaleX", 0f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(profileImage, "scaleY", 0f, 1.2f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(profileImage, "rotation", 0f, 360f);

        scaleX.setDuration(1500);
        scaleY.setDuration(1500);
        rotation.setDuration(2000);

        scaleX.setInterpolator(new BounceInterpolator());
        scaleY.setInterpolator(new BounceInterpolator());
        rotation.setInterpolator(new DecelerateInterpolator());

        // Shadow animation
        ObjectAnimator shadowFadeIn = ObjectAnimator.ofFloat(imageShadow, "alpha", 0f, 0.3f);
        shadowFadeIn.setDuration(1000);
        shadowFadeIn.setStartDelay(500);

        // Glow effect animation
        ObjectAnimator glowFadeIn = ObjectAnimator.ofFloat(glowEffect, "alpha", 0f, 0.6f, 0.3f);
        glowFadeIn.setDuration(2000);
        glowFadeIn.setStartDelay(800);
        glowFadeIn.setRepeatCount(ValueAnimator.INFINITE);
        glowFadeIn.setRepeatMode(ValueAnimator.REVERSE);

        // Name text animation
        ObjectAnimator nameSlideUp = ObjectAnimator.ofFloat(nameText, "translationY", 100f, 0f);
        ObjectAnimator nameFadeIn = ObjectAnimator.ofFloat(nameText, "alpha", 0f, 1f);
        nameSlideUp.setDuration(1000);
        nameFadeIn.setDuration(1000);
        nameSlideUp.setStartDelay(1200);
        nameFadeIn.setStartDelay(1200);
        nameSlideUp.setInterpolator(new DecelerateInterpolator());

        // Subtitle animation
        ObjectAnimator subtitleSlideUp = ObjectAnimator.ofFloat(subtitleText, "translationY", 50f, 0f);
        ObjectAnimator subtitleFadeIn = ObjectAnimator.ofFloat(subtitleText, "alpha", 0f, 1f);
        subtitleSlideUp.setDuration(800);
        subtitleFadeIn.setDuration(800);
        subtitleSlideUp.setStartDelay(1800);
        subtitleFadeIn.setStartDelay(1800);

        // Floating animation for name text
        ObjectAnimator nameFloat = ObjectAnimator.ofFloat(nameText, "translationY", 0f, -10f, 0f);
        nameFloat.setDuration(3000);
        nameFloat.setRepeatCount(ValueAnimator.INFINITE);
        nameFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        nameFloat.setStartDelay(2500);

        // 3D rotation effect for image
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(profileImage, "rotationX", 0f, 15f, 0f, -15f, 0f);
        rotationX.setDuration(4000);
        rotationX.setRepeatCount(ValueAnimator.INFINITE);
        rotationX.setStartDelay(3000);

        welcomeAnimations.playTogether(
                scaleX, scaleY, rotation, shadowFadeIn, glowFadeIn,
                nameSlideUp, nameFadeIn, subtitleSlideUp, subtitleFadeIn,
                nameFloat, rotationX
        );

        welcomeAnimations.start();
    }

    private void transitionToHome() {
        // Stop welcome animations
        if (welcomeAnimations != null) {
            welcomeAnimations.cancel();
        }

        // Fade out welcome screen
        ObjectAnimator welcomeFadeOut = ObjectAnimator.ofFloat(welcomeContainer, "alpha", 1f, 0f);
        welcomeFadeOut.setDuration(500);
        welcomeFadeOut.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                welcomeContainer.setVisibility(View.GONE);
                homeContainer.setVisibility(View.VISIBLE);

                // Fade in home screen
                homeContainer.setAlpha(0f);
                ObjectAnimator homeFadeIn = ObjectAnimator.ofFloat(homeContainer, "alpha", 0f, 1f);
                homeFadeIn.setDuration(500);
                homeFadeIn.start();

                // Animate home content
                animateHomeContent();
            }
        });
        welcomeFadeOut.start();
    }

    private void animateHomeContent() {
        // Slide in animation for home content
        homeContent.setTranslationY(50f);
        homeContent.setAlpha(0f);

        ObjectAnimator slideUp = ObjectAnimator.ofFloat(homeContent, "translationY", 50f, 0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(homeContent, "alpha", 0f, 1f);

        slideUp.setDuration(800);
        fadeIn.setDuration(800);
        slideUp.setInterpolator(new DecelerateInterpolator());

        AnimatorSet homeAnimations = new AnimatorSet();
        homeAnimations.playTogether(slideUp, fadeIn);
        homeAnimations.start();
    }

    private void setupBottomNavigation() {
        navHome.setOnClickListener(v -> switchToTab("home"));
        navAbout.setOnClickListener(v -> switchToTab("about"));
        navProfile.setOnClickListener(v -> switchToTab("profile"));
    }

    private void switchToTab(String tab) {
        // Hide all content
        homeContent.setVisibility(View.GONE);
        aboutContent.setVisibility(View.GONE);
        profileContent.setVisibility(View.GONE);

        // Reset navigation states
        updateNavigationColors();

        // Show selected content with animation
        LinearLayout targetContent = null;
        ImageView targetIcon = null;
        TextView targetText = null;

        switch (tab) {
            case "home":
                targetContent = homeContent;
                targetIcon = navHomeIcon;
                targetText = navHomeText;
                break;
            case "about":
                targetContent = aboutContent;
                targetIcon = navAboutIcon;
                targetText = navAboutText;
                break;
            case "profile":
                targetContent = profileContent;
                targetIcon = navProfileIcon;
                targetText = navProfileText;
                break;
        }

        if (targetContent != null && targetIcon != null && targetText != null) {
            // Highlight selected tab
            ImageViewCompat.setImageTintList(targetIcon, ColorStateList.valueOf(ACCENT_COLOR));
            targetText.setTextColor(ACCENT_COLOR);

            // Animate content
            targetContent.setVisibility(View.VISIBLE);
            targetContent.setAlpha(0f);
            targetContent.setTranslationY(30f);

            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(targetContent, "alpha", 0f, 1f);
            ObjectAnimator slideUp = ObjectAnimator.ofFloat(targetContent, "translationY", 30f, 0f);

            fadeIn.setDuration(400);
            slideUp.setDuration(400);
            slideUp.setInterpolator(new DecelerateInterpolator());

            AnimatorSet tabAnimation = new AnimatorSet();
            tabAnimation.playTogether(fadeIn, slideUp);
            tabAnimation.start();

            // Add tab selection animation
            animateTabSelection(targetIcon);
        }
    }

    private void animateTabSelection(ImageView icon) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(icon, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(icon, "scaleY", 1f, 1.2f, 1f);

        scaleX.setDuration(200);
        scaleY.setDuration(200);

        AnimatorSet scaleAnimation = new AnimatorSet();
        scaleAnimation.playTogether(scaleX, scaleY);
        scaleAnimation.start();
    }

    private void updateNavigationColors() {
        int inactiveColor = isDarkMode ? 0xFF7F8C8D : 0xFF95A5A6;

        ImageViewCompat.setImageTintList(navHomeIcon, ColorStateList.valueOf(inactiveColor));
        ImageViewCompat.setImageTintList(navAboutIcon, ColorStateList.valueOf(inactiveColor));
        ImageViewCompat.setImageTintList(navProfileIcon, ColorStateList.valueOf(inactiveColor));
        navHomeText.setTextColor(inactiveColor);
        navAboutText.setTextColor(inactiveColor);
        navProfileText.setTextColor(inactiveColor);

        // Set home as active by default
        ImageViewCompat.setImageTintList(navHomeIcon, ColorStateList.valueOf(ACCENT_COLOR));
        navHomeText.setTextColor(ACCENT_COLOR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (welcomeAnimations != null) {
            welcomeAnimations.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (homeContainer.getVisibility() == View.VISIBLE) {
            // If on home screen, minimize app instead of closing
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }
}