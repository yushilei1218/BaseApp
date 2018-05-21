package com.shileiyu.baseapp.common.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模式
 *
 * @author shilei.yu
 * @since on 2018/5/21.
 */
public class ChainOfResponsibilityPattern {
    public static class Response {
        String tag;

        public Response(String tag) {
            this.tag = tag;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "tag='" + tag + '\'' +
                    '}';
        }
    }

    public static class Request {
        public String tag = "1";
    }

    public interface Chain {
        Response process(Request request);
    }

    public static class RealChain implements Chain {
        List<Interceptor> interceptors;
        int index;
        Request request;

        public RealChain(List<Interceptor> interceptors, int index, Request request) {
            this.interceptors = interceptors;
            this.index = index;
            this.request = request;
        }

        @Override
        public Response process(Request request) {
            Interceptor interceptor = interceptors.get(index);
            RealChain realChain = new RealChain(interceptors, index + 1, this.request);
            return interceptor.intercept(realChain);
        }
    }

    public interface Interceptor {
        Response intercept(Chain chain);
    }

    public static class AInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) {
            Request request = ((RealChain) chain).request;
            request.tag += " 2";
            return chain.process(request);
        }
    }

    public static class BInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) {
            Request request = ((RealChain) chain).request;
            return new Response(request.tag+" end");
        }
    }

    public static void main(String[] a) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new AInterceptor());
        interceptors.add(new AInterceptor());
        interceptors.add(new AInterceptor());
        interceptors.add(new BInterceptor());
        Request request = new Request();
        Chain chain = new RealChain(interceptors, 0, request);
        Response process = chain.process(request);

        System.out.print(process.toString());
    }
}
