/*
 * This file is part of OpenByte IDE, licensed under the MIT License (MIT).
 *
 * Copyright (c) TorchPowered 2016
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.openbyte.event.handle;

import net.openbyte.event.meta.Event;
import net.openbyte.event.meta.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Handles all events execution and handling.
 */
public class EventManager {
    private static EventManager RUNTIME_INSTANCE = null;

    private ArrayList<Class<?>> listenerClasses = new ArrayList<Class<?>>();

    private EventManager(){}

    public static EventManager init() {
        if(RUNTIME_INSTANCE == null) {
            RUNTIME_INSTANCE = new EventManager();
        }
        return getManager();
    }

    public static EventManager getManager() {
        return RUNTIME_INSTANCE;
    }

    public void registerListener(Class<?> listener) {
        for (Class<?> listenerClass : listenerClasses) {
            if (listenerClass.getName().equals(listener.getName())) {
                return;
            }
        }
        if(!listener.isAnnotationPresent(EventListener.class)) {
            return;
        }
        listenerClasses.add(listener);
    }

    public void callEvent(Event event) throws Exception {
        ArrayList<Method> methodsRelated = new ArrayList<Method>();
        for (Class<?> listener : listenerClasses) {
            for (Method method : listener.getDeclaredMethods()) {
                for (Class<?> parameterType : method.getParameterTypes()) {
                    if(parameterType == event.getClass()) {
                        methodsRelated.add(method);
                    }
                }
            }
        }
        Class<?> eventClass = event.getClass();
        for (Method method : methodsRelated) {
            Object classInstance = method.getDeclaringClass().newInstance();
            method.invoke(classInstance, eventClass.cast(event));
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Setting up testing objects...");
        EventManager.init();
        System.out.println("Registering the listener...");
        EventManager.getManager().registerListener(TestEventListener.class);
        System.out.println("Running dummy event tests...");
        DummyEvent event = new DummyEvent();
        EventManager.getManager().callEvent(event);
    }

    public static class DummyEvent implements Event {
        public String getDummyMessage() {
            return "test";
        }
    }

    @EventListener
    public static class TestEventListener {
        public void handleDummyEvent(DummyEvent e) {
            System.out.println("Received dummy event with result:");
            System.out.println(e.getDummyMessage());
            System.exit(0);
        }
    }
}
