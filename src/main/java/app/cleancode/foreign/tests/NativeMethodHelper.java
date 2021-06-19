package app.cleancode.foreign.tests;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.nio.file.Path;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;

public class NativeMethodHelper {
    public static final NativeMethodHelper STANDARD_LIBRARY =
            new NativeMethodHelper(LibraryLookup.ofDefault());

    private final LibraryLookup library;

    public NativeMethodHelper(LibraryLookup library) {
        this.library = library;
    }

    public MethodHandle get(String symbol, MethodType javaMethodType,
            FunctionDescriptor nativeFunctionDescriptor) {
        return CLinker.getInstance().downcallHandle(library.lookup(symbol).get(), javaMethodType,
                nativeFunctionDescriptor);
    }

    public static NativeMethodHelper ofLibraryName(String libraryName) {
        return new NativeMethodHelper(LibraryLookup.ofLibrary(libraryName));
    }

    public static NativeMethodHelper ofPath(Path path) {
        return new NativeMethodHelper(LibraryLookup.ofPath(path));
    }
}
