package org.firstinspires.ftc.teamcode.nathan;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


public class Robot {
    private DcMotor lift = null;
    private CRServo wrist = null;
    private CRServo collection = null;
    private CRServo bucket = null;
    private DcMotor extension = null;
    public Robot(DcMotor lift,  DcMotor extension, CRServo wrist, CRServo bucket, CRServo collection) {
        this.lift = lift;
        this.extension = extension;
        this.wrist = wrist;
        this.bucket = bucket;
        this.collection = collection;

    }
    public void liftUp()
    {
        lift.setTargetPosition(1120 * 2 + 1750);
        while(lift.isBusy()) {
            lift.setPower(.25);
        }
        lift.setPower(0);
    }
    public void liftDown()
    {
        lift.setTargetPosition(0);
        while(lift.isBusy()){
            lift.setPower(-.25);
        }
        lift.setPower(0);
    }
    public void wristUp() {
        wrist.setPower(-.8);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wrist.setPower(0);
    }
    public void wristDown() {
        wrist.setPower(.8);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wrist.setPower(0);
    }
    public void extendOut()
    {
        extension.setPower(-.75);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        extension.setPower(0);
    }
    public void extendIn()
    {
        extension.setPower(.75);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        extension.setPower(0);
    }
}
