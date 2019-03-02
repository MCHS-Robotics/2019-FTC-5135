package org.firstinspires.ftc.teamcode.nathan;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="ServoPositiontest", group="Iterative Opmode")
//@Disabled
public class ServoPositionTest extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor lift = null;
    private CRServo wrist = null;
    private boolean bucketOverride = false;
    //private DcMotor fBucket = null;
    private CRServo collection = null;
    private Servo bucket = null;
    private DcMotor extension = null;
    boolean bucketUp = false;
    boolean wristUp = false;

    /* extension gamepad 1
    wrist on triggers return double
    extension on bumpers
    bucket gamepad 2

    */

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        wrist = hardwareMap.crservo.get("wrist");
        extension = hardwareMap.get(DcMotor.class, "extension");
        lift = hardwareMap.get(DcMotor.class, "lift");


        //lift = hardwareMap.get(DcMotor.class, "lift");
        bucket = hardwareMap.servo.get("bucket");
        bucket.setPosition(-1);
        //fBucket = hardwareMap.get(DcMotor.class, "fBucket");
        //fBucket.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        collection = hardwareMap.crservo.get("collection");
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.REVERSE);
        //lift.setDirection(DcMotor.Direction.FORWARD);
       // fBucket.setDirection(DcMotor.Direction.FORWARD);


        //wrist.setPosition(-1);
        //telemetry.addData("fBucket", fBucket.getPower());
        //Robot robot = new Robot(lift, extension, wrist, bucket, collection, drive);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //bucket.setPosition(gamepad1.left_stick_y);
        telemetry.addData("Bucket Position", bucket.getPosition());
        telemetry.update();
        if(gamepad1.a)
        {
            bucket.setPosition(bucket.getPosition()+0.0025);
        }
        else if(gamepad1.b)
        {
            bucket.setPosition(bucket.getPosition()-0.0025);
        }
    }

    /*;
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    /**
     * Loads the mineral into the the rear bucket
     * @param robot
     */
//    public void loadMineral(Robot robot)
//    {
//        robot.extendIn();
//        robot.wristUp();
//        robot.wristDown();
//    }
//
//    public void dumpMineral(Robot robot)
//    {
//        robot.liftUp();
//        robot.bucketUp();
//        robot.liftDown();
//    }
//
//    public void preLatch(Robot robot)
//    {
//        robot.liftUp();
//        robot.bucketUp();
//    }

}