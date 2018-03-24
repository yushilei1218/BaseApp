package com.shileiyu.baseapp.common.pattern;

/**
 * @author shilei.yu
 * @since on 2018/3/24.
 */

public class StatePattern {

    public static void main(String[] args) {
        ConcreteElevator elevator = new ConcreteElevator();
        elevator.setState(BaseElevator.STOP_STATE);
        elevator.open();
        elevator.run();
        elevator.close();
        elevator.stop();

        Context context = new Context();
        context.setElevatorState(context.stopState);
        context.open();
        context.close();
        context.run();
        context.stop();
    }

    public interface Action {
        void open();

        void run();

        void stop();

        void close();
    }

    public static abstract class ElevatorSate implements Action {
        protected Context mContext;

        public ElevatorSate(Context context) {
            mContext = context;
        }

        public void setContext(Context c) {
            mContext = c;
        }
    }

    public static class OpenState extends ElevatorSate {

        public OpenState(Context context) {
            super(context);
        }

        @Override
        public void open() {

        }

        @Override
        public void run() {

        }

        @Override
        public void stop() {
        }

        @Override
        public void close() {
            mContext.setElevatorState(mContext.closeState);
        }
    }

    public static class CloseState extends ElevatorSate {
        public CloseState(Context context) {
            super(context);
        }

        @Override
        public void open() {
            mContext.setElevatorState(mContext.openState);
        }

        @Override
        public void run() {
            mContext.setElevatorState(mContext.runState);
        }

        @Override
        public void stop() {
        }

        @Override
        public void close() {

        }
    }

    public static class RunState extends ElevatorSate {

        public RunState(Context context) {
            super(context);
        }

        @Override
        public void open() {

        }

        @Override
        public void run() {

        }

        @Override
        public void stop() {
            mContext.setElevatorState(mContext.stopState);
        }

        @Override
        public void close() {

        }
    }

    public static class StopState extends ElevatorSate {

        public StopState(Context context) {
            super(context);
        }

        @Override
        public void open() {

        }

        @Override
        public void run() {

        }

        @Override
        public void stop() {

        }

        @Override
        public void close() {
            mContext.setElevatorState(mContext.closeState);
        }
    }

    public static class Context implements Action {
        private ElevatorSate state;

        public ElevatorSate openState = new OpenState(this);
        public ElevatorSate closeState = new CloseState(this);
        public ElevatorSate runState = new RunState(this);
        public ElevatorSate stopState = new StopState(this);


        public void setElevatorState(ElevatorSate state) {
            this.state = state;

            System.out.println("正在进入" + state);
        }

        public ElevatorSate getState() {
            return state;
        }

        @Override
        public void open() {
            state.open();
        }

        @Override
        public void run() {
            state.run();
        }

        @Override
        public void stop() {
            state.stop();
        }

        @Override
        public void close() {
            state.close();
        }
    }


    public static abstract class BaseElevator {
        public static final int RUNNING_STATE = 0x1;
        public static final int STOP_STATE = 0x2;
        public static final int CLOSE_STATE = 0x3;
        public static final int OPEN_STATE = 0x4;

        public abstract void setState(int state);

        public abstract void open();

        public abstract void close();

        public abstract void stop();

        public abstract void run();
    }


    @Deprecated
    public static class ConcreteElevator extends BaseElevator {
        private int state;

        @Override
        public void setState(int state) {
            this.state = state;
        }

        @Override
        public void open() {
            switch (state) {
                case RUNNING_STATE:
                    return;
                case STOP_STATE:
                    setState(OPEN_STATE);
                    break;
                case CLOSE_STATE:
                    setState(OPEN_STATE);
                    break;
                case OPEN_STATE:
                    return;
                default:
                    break;
            }
            processState();
        }

        @Override
        public void close() {
            switch (state) {
                case RUNNING_STATE:
                    return;
                case STOP_STATE:
                    return;
                case CLOSE_STATE:
                    return;
                case OPEN_STATE:
                    setState(CLOSE_STATE);
                    break;
                default:
                    break;
            }
            processState();
        }

        @Override
        public void stop() {
            switch (state) {
                case RUNNING_STATE:
                    setState(STOP_STATE);
                    break;
                case STOP_STATE:
                    return;
                case CLOSE_STATE:
                    setState(STOP_STATE);
                    break;
                case OPEN_STATE:
                    return;
                default:
                    break;
            }
            processState();
        }

        @Override
        public void run() {
            switch (state) {
                case RUNNING_STATE:
                    return;
                case STOP_STATE:
                    setState(RUNNING_STATE);
                    break;
                case CLOSE_STATE:
                    setState(RUNNING_STATE);
                    break;
                case OPEN_STATE:
                    return;
                default:
                    break;
            }
            processState();
        }

        private void processState() {
            String msg = "";
            switch (state) {
                case RUNNING_STATE:
                    msg = "运行状态";
                    break;
                case STOP_STATE:
                    msg = "停止状态";
                    break;
                case CLOSE_STATE:
                    msg = "关闭状态";
                    break;
                case OPEN_STATE:
                    msg = "开门状态";
                    break;
                default:
                    break;
            }
            System.out.println("电梯开始进入" + msg);
        }
    }


}
