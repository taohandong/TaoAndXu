//
// Created by ToaHanDong on 2018/1/6.
//
#include <jni.h>
#include <opencv2/opencv.hpp>
#include <vector>

using namespace cv;
using namespace std;

extern "C" {
void Java_com_share_shareapplication_OpenCvActivity_nativeProcessFrame(JNIEnv *env, jobject thiz, jlong addrGray, jlong addrRGBA){
    Mat& gray = *(Mat *) addrGray;
    Mat& rgba = *(Mat *) addrRGBA;
    vector<KeyPoint> v;

    Ptr<ORB> orb = ORB::create();
    orb->detect(gray, v, cv::Mat());

    for (int i = 0; i < v.size(); ++i) {
        const KeyPoint& kp = v[i];
        circle(rgba, Point(kp.pt.x, kp.pt.y), 10, Scalar(255,0,0,255));
    }
}
}

