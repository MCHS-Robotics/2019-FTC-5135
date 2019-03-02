package org.firstinspires.ftc.teamcode.nathan;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class Robot {
    private DcMotor lift = null;
    private CRServo wrist = null;
    private CRServo collection = null;
    private Servo bucket = null;
    private DcMotor extension = null;
    private NormalDriveEncoders drive = null;
    private LinearOpMode opmode = null;
    public Robot(DcMotor lift, DcMotor extension, CRServo wrist, Servo bucket,
                 CRServo collection, NormalDriveEncoders drive, LinearOpMode opmode) {
        this.lift = lift;
        this.extension = extension;
        this.wrist = wrist;
        this.bucket = bucket;
        this.collection = collection;
        this.drive = drive;
        this.opmode = opmode;
    }

    /**
     * Raises the lift up to unlatch the robot
     */
    public void liftUp()
    {
        if(opmode.opModeIsActive()) {
            lift.setTargetPosition(3160);
            while (lift.isBusy()) {
                lift.setPower(1);
            }
            lift.setPower(0);
        }
    }

    /**
     * Lowers the lift back to starting position
     */
    public void liftDown()
    {
        if(opmode.opModeIsActive()) {
            lift.setTargetPosition(0);
            while (lift.isBusy()) {
                lift.setPower(-1);
            }
            lift.setPower(0);
        }
    }
    /**
     * Raises the wrist
     **/
    public void forward(float inches) {
        if(opmode.opModeIsActive()) {
            drive.forward(inches);
        }
    }

    public void backward(float inches) {
        if(opmode.opModeIsActive()) {
            drive.backward(inches);
        }
    }

    public void pivotLeft(float degrees){
        if(opmode.opModeIsActive()) {
            drive.pivotLeft(degrees);
        }
    }

    public void pivotRight(float degrees){
        if(opmode.opModeIsActive()) {
            drive.pivotRight(degrees);
        }
    }

    /**
     * Lowers the wrist
     */
    public void wristDown() {
        if(opmode.opModeIsActive()) {
            wrist.setPower(.8);
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrist.setPower(0);
        }
    }

    /**
     * Raises the wrist
     */
    public void wristUp() {
        if(opmode.opModeIsActive()) {
            wrist.setPower(-.8);
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrist.setPower(0);
        }
    }    /**
     * Extends the front arm
     */
    public void extendOut()
    {
        if(opmode.opModeIsActive()) {
            extension.setPower(-1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            extension.setPower(0);
        }
    }

    /**
     * Contracts the front arm
     */
    public void extendIn()
    {
        if(opmode.opModeIsActive()) {
            extension.setPower(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            extension.setPower(0);
        }
    }

    /**
     * This method collects
     * @param milliseconds
     */
    public void collectIn(int milliseconds)
    {
        if(opmode.opModeIsActive()) {
            collection.setPower(.8);
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            collection.setPower(0);
        }
    }

    /**
     * Unloads whatever is inside the collector
     * @param milliseconds
     */
    public void collectOut(int milliseconds)
    {
        if(opmode.opModeIsActive()) {
            collection.setPower(.8);
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            collection.setPower(0);
        }
    }

    public void bucketDown()
    {
        if(opmode.opModeIsActive()) {

            bucket.setPosition(-1);
        }
    }

    public void bucketUp()
    {
        if(opmode.opModeIsActive()) {
            bucket.setPosition(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
