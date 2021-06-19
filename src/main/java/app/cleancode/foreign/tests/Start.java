package app.cleancode.foreign.tests;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

public class Start {
    public static void main(String[] args) {
        MethodHandle strlenHandle = NativeMethodHelper.STANDARD_LIBRARY.get("strlen",
                MethodType.methodType(Integer.TYPE, MemoryAddress.class),
                FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_POINTER));
        try (MemorySegment str = CLinker.toCString("Hello, World!")) {
            System.out.println((int) strlenHandle.invokeExact(str.address()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
