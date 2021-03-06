package org.firstinspires.ftc.teamcode.nathan;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class RobotCR {
    private DcMotor lift = null;
    private CRServo wrist = null;
    private CRServo collection = null;
    private CRServo bucket = null;
    private DcMotor extension = null;
    private NormalDriveEncoders drive = null;
    public RobotCR(DcMotor lift, DcMotor extension, CRServo wrist, CRServo bucket,
                   CRServo collection, NormalDriveEncoders drive) {
        this.lift = lift;
        this.extension = extension;
        this.wrist = wrist;
        this.bucket = bucket;
        this.collection = collection;
        this.drive = drive;

    }

    /**
     * Raises the lift up to unlatch the robot
     */
    public void liftUp()
    {
        lift.setTargetPosition(3160);
        while(lift.isBusy()) {
            lift.setPower(1);
        }
        lift.setPower(0);
    }

    /**
     * Lowers the lift back to starting position
     */
    public void liftDown()
    {
        lift.setTargetPosition(0);
        while(lift.isBusy()){
            lift.setPower(-1);
        }
        lift.setPower(0);
    }
    /**
     * Raises the wrist
     */
    public void wristUp() {
        wrist.setPower(-.8);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wrist.setPower(0);

    }

    public void forward(float inches) {
        drive.forward(inches);
    }

    public void backward(float inches) {
        drive.backward(inches);
    }

    public void pivotLeft(float degrees){
        drive.pivotLeft(degrees);
    }

    public void pivotRight(float degrees){
        drive.pivotRight(degrees);
    }

    /**
     * Lowers the wrist
     */
    public void wristDown() {
        wrist.setPower(.8);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wrist.setPower(0);
    }

    /**
     * Extends the front arm
     */
    public void extendOut()
    {
        extension.setPower(-1);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        extension.setPower(0);
    }

    /**
     * Contracts the front arm
     */
    public void extendIn()
    {
        extension.setPower(1);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        extension.setPower(0);
    }

    /**
     * This method collects
     * @param milliseconds
     */
    public void collectIn(int milliseconds)
    {
        collection.setPower(.8);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        collection.setPower(0);
    }

    /**
     * Unloads whatever is inside the collector
     * @param milliseconds
     */
    public void collectOut(int milliseconds)
    {
        collection.setPower(.8);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        collection.setPower(0);
    }

    public void bucketDown()
    {
        bucket.setPower(-8);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bucket.setPower(0);
    }

    public void bucketUp()
    {
        bucket.setPower(8);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bucket.setPower(0);

    }
}
