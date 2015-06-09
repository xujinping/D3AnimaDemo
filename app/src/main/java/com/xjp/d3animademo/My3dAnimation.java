/*
 * @Title: My3dAnimation.java
 * @Description: TODO<请描述此文件是做什么的>
 * @author: xjp
 * @data: 2014年9月15日 上午8:54:10
 * @version: V1.0
 */
package com.xjp.d3animademo;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * TODO<请描述这个类是干什么的>
 *
 * @author xjp
 * @data: 2014年9月15日 上午8:54:10
 * @version: V1.0
 */
public class My3dAnimation extends Animation {

    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private Camera mCamera;
    private int mDirection;
    private final static int ROTATE_X = 0;//沿着x轴旋转
    private final static int ROTATE_Y = 1;//沿着y轴旋转

    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space, definied by a pair of
     * X and Y coordinates, called centerX and centerY. When the animation
     * starts, a translation on the Z axis (depth) is performed. The length of
     * the translation can be specified, as well as whether the translation
     * should be reversed in time.
     *
     * @param direction
     *            the direction of the 3D rotation
     * @param fromDegrees
     *            the start angle of the 3D rotation
     * @param toDegrees
     *            the end angle of the 3D rotation
     * @param centerX
     *            the X center of the 3D rotation
     * @param centerY
     *            the Y center of the 3D rotation
     */
    public My3dAnimation(int direction, float fromDegrees, float toDegrees,
                         float centerX, float centerY, float depthZ) {
        mDirection = direction;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
    }

    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees
                + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();

        if (centerX!=0){
            if (interpolatedTime < 0.5) {
                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
            } else {
                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
            }
        }

        switch (mDirection) {
            case ROTATE_X:
                camera.rotateX(degrees);
                break;
            case ROTATE_Y:
                camera.rotateY(degrees);
                break;
        }

        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
