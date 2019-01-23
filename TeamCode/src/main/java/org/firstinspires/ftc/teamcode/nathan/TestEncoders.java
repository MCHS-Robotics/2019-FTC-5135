
package org.firstinspires.ftc.teamcode.nathan;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

        @TeleOp(name="TestEncoders", group="Iterative Opmode")
//@Disabled
        public class TestEncoders extends OpMode
        {
            // Declare OpMode members.
            private ElapsedTime runtime = new ElapsedTime();
            private DcMotor left = null;
            private DcMotor right = null;

            /*
             * Code to run ONCE when the driver hits INIT
             */
            @Override
            public void init() {
                telemetry.addData("Status", "Initialized");

                left = hardwareMap.get(DcMotor.class, "left");
                right = hardwareMap.get(DcMotor.class, "right");
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                // Most robots need the motor on one side to be reversed to drive forward
                // Reverse the motor that runs backwards when connected directly to the battery
                left.setDirection(DcMotor.Direction.REVERSE);
                right.setDirection(DcMotor.Direction.REVERSE);


                // Tell the driver that initialization is complete.
                telemetry.addData("Left Encoder", left.getCurrentPosition());
                telemetry.addData("runtime", getRuntime());
                telemetry.addData("Right Encoder", right.getCurrentPosition());
                telemetry.addData("Status", "Initialized");


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


                telemetry.update();
            }

            /*;
             * Code to run ONCE after the driver hits STOP
             */
            @Override
            public void stop() {
            }

        }



