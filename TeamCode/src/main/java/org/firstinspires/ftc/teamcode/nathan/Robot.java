package org.firstinspires.ftc.teamcode.nathan;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Robot {


    final private int encoder = 1120;
    final private float turnRadius =  17.8f;

    private DcMotor lift, left, right, extension;
    private CRServo wrist, collection;
    private Servo bucket = null;
    private LinearOpMode opmode = null;
    private Telemetry telemetry = null;
    private float power = 0.5f;
    public Robot(DcMotor left, DcMotor right, float power, DcMotor lift, DcMotor extension, CRServo wrist, Servo bucket,
                 CRServo collection, LinearOpMode opmode, Telemetry telemetry) {
        this.lift = lift;
        this.extension = extension;
        this.wrist = wrist;
        this.bucket = bucket;
        this.collection = collection;
        this.opmode = opmode;
        this.left = left;
        this.right = right;
        this.telemetry = telemetry;
        this.power = power;
    }

    /**
     * Raises the lift up to unlatch the robot
     */
    public void liftUp() {
        if (opmode.opModeIsActive()) {
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
    public void liftDown() {
        if (opmode.opModeIsActive()) {
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
        if (opmode.opModeIsActive()) {
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            int pos = (int)((encoder * inches)/(4 * Math.PI));
            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            left.setTargetPosition(pos);
            right.setTargetPosition(pos);
            right.setPower(power);
            left.setPower(power);
            while(left.isBusy() && right.isBusy() && opmode.opModeIsActive())
            {
                telemetry.addData("Motor Encoder", "Left Pos: " + left.getCurrentPosition());
                telemetry.addLine();
                telemetry.addData("Motor Encoder", "Right Pos: " + right.getCurrentPosition());
                telemetry.addLine();
                telemetry.addData("Power","Left Pow: " + left.getPower());
                telemetry.addLine();
                telemetry.addData("Power","Right Pow: " + right.getPower());
                telemetry.addLine();
                telemetry.addData("Target","Left Tar: " + left.getTargetPosition());
                telemetry.update();
            }
            left.setPower(0);
            right.setPower(0);
            telemetry.update();
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void backward(float inches) {
        if (opmode.opModeIsActive()) {
            forward(-inches);
        }

    }

    public void pivotLeft(float degrees) {
        if (opmode.opModeIsActive()) {
            double arc = Math.PI * turnRadius * degrees / 360f;
            int pos = -(int)((encoder * arc)/(4 * Math.PI));
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            left.setTargetPosition(pos);
            right.setTargetPosition(-pos);
            left.setPower(power);
            right.setPower(power);
            while(left.isBusy() && right.isBusy() && opmode.opModeIsActive())
            {
                telemetry.addData("Motor Encoder", "Left Pos: " + left.getCurrentPosition());
                telemetry.addLine();
                telemetry.addData("Motor Encoder", "Right Pos: " + right.getCurrentPosition());
                telemetry.addLine();
                telemetry.addData("Power","Left Pow: " + left.getPower());
                telemetry.addLine();
                telemetry.addData("Power","Right Pow: " + right.getPower());
                telemetry.addLine();
                telemetry.addData("Target","Left Tar: " + left.getTargetPosition());
                telemetry.update();
                //telemetry.addData("Target","Right Tar: " + right.getTargetPosition());
                //telemetry.update();
            }
            left.setPower(0);
            right.setPower(0);
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void pivotRight(float degrees) {
        if (opmode.opModeIsActive()) {
            pivotLeft(-degrees);
        }
    }

    /**
     * Lowers the wrist
     */
    public void wristDown() {
        if (opmode.opModeIsActive()) {
            wrist.setPower(.8);
            try {
                Thread.sleep(450);
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
        if (opmode.opModeIsActive()) {
            wrist.setPower(-.8);
            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrist.setPower(0);
        }
    }

    /**
     * Extends the front arm
     */
    public void extendOut() {
        if (opmode.opModeIsActive()) {
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
    public void extendIn() {
        if (opmode.opModeIsActive()) {
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
     *
     * @param milliseconds
     */
    public void collectIn(int milliseconds) {
        if (opmode.opModeIsActive()) {
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
     *
     * @param milliseconds
     */
    public void collectOut(int milliseconds) {
        if (opmode.opModeIsActive()) {
            collection.setPower(.8);
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            collection.setPower(0);
        }
    }

    public void bucketDown() {
        if (opmode.opModeIsActive()) {

            bucket.setPosition(0);
        }
    }

    public void bucketUp() {
        if (opmode.opModeIsActive()) {
            bucket.setPosition(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * Created by student on 1/17/18.
 */
