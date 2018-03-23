package com.shileiyu.baseapp.common.pattern;

/**
 * 中介者模式
 *
 * @author shilei.yu
 * @since on 2018/3/23.
 */

public class MediatorPattern {
    /**
     * 中介
     */
    public static abstract class Mediator {
        protected ConcreteColleague1 c1;
        protected ConcreteColleague2 c2;

        public void setC1(ConcreteColleague1 c1) {
            this.c1 = c1;
        }

        public void setC2(ConcreteColleague2 c2) {
            this.c2 = c2;
        }

        public abstract void doSomething1();

        public abstract void doSomething2();
    }

    /**
     * 同事
     */
    public static abstract class Colleague {
        protected Mediator mMediator;

        public Colleague(Mediator mediator) {
            mMediator = mediator;
        }

        public abstract void doSelfThing();

        public abstract void doOtherThing();
    }

    public static class ConcreteColleague1 extends Colleague {

        public ConcreteColleague1(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void doSelfThing() {
            log("doSelfThing " + this);
        }

        @Override
        public void doOtherThing() {
            log("doOtherThing " + this);
        }
    }

    public static class ConcreteColleague2 extends Colleague {

        public ConcreteColleague2(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void doSelfThing() {
            log("doSelfThing " + this);
        }

        @Override
        public void doOtherThing() {
            log("doOtherThing " + this);
        }
    }

    public static class ConcreteMediator extends Mediator {

        @Override
        public void doSomething1() {
            c1.doOtherThing();
            c2.doOtherThing();
        }

        @Override
        public void doSomething2() {
            c1.doSelfThing();
            c2.doSelfThing();
        }
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        ConcreteMediator mediator = new ConcreteMediator();
        ConcreteColleague1 colleague1 = new ConcreteColleague1(mediator);
        ConcreteColleague2 colleague2 = new ConcreteColleague2(mediator);
        mediator.setC1(colleague1);
        mediator.setC2(colleague2);
        mediator.doSomething1();
        mediator.doSomething2();
    }
}
