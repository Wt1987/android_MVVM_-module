/*
 * Copyright (C) 2017 贵阳货车帮科技有限公司
 */

package com.util.thirdparty;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Java7 feature detection and reflection based feature access.
 * <p/>
 * Taken from maven-shared-utils, only for private usage until we go full java7
 */
final class Java7Support {

    private static final boolean IS_JAVA7;

    private static Method sIsSymbolicLink;

    private static Method sDelete;

    private static Method sToPath;

    private static Method sExists;

    private static Method sToFile;

    private static Method sReadSymlink;

    private static Method sCreateSymlink;

    private static Object sEmptyLinkOpts;

    private static Object sEmptyFileAttributes;

    static {
        boolean isJava7x = true;
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class<?> files = cl.loadClass("java.nio.file.Files");
            Class<?> path = cl.loadClass("java.nio.file.Path");
            Class<?> fa = cl.loadClass("java.nio.file.attribute.FileAttribute");
            Class<?> linkOption = cl.loadClass("java.nio.file.LinkOption");
            sIsSymbolicLink = files.getMethod("isSymbolicLink", path);
            sDelete = files.getMethod("delete", path);
            sReadSymlink = files.getMethod("readSymbolicLink", path);

            sEmptyFileAttributes = Array.newInstance(fa, 0);
            sCreateSymlink = files.getMethod("createSymbolicLink", path, path, sEmptyFileAttributes.getClass());
            sEmptyLinkOpts = Array.newInstance(linkOption, 0);
            sExists = files.getMethod("exists", path, sEmptyLinkOpts.getClass());
            sToPath = File.class.getMethod("toPath");
            sToFile = path.getMethod("toFile");
        } catch (ClassNotFoundException e) {
            isJava7x = false;
        } catch (NoSuchMethodException e) {
            isJava7x = false;
        }
        IS_JAVA7 = isJava7x;
    }

    private Java7Support() {
        throw new AssertionError("Don't instance! ");
    }

    /**
     * Invokes java7 isSymbolicLink
     *
     * @param file The file to check
     * @return true if the file is a symbolic link
     */
    public static boolean isSymLink(File file) {
        try {
            Object path = sToPath.invoke(file);
            Boolean result = (Boolean) sIsSymbolicLink.invoke(null, path);
            return result.booleanValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the target of a symbolic link
     *
     * @param symlink The symlink to read
     * @return The location the symlink is pointing to
     * @throws IOException Upon failure
     */

    public static File readSymbolicLink(File symlink)
            throws IOException {
        try {
            Object path = sToPath.invoke(symlink);
            Object resultPath = sReadSymlink.invoke(null, path);
            return (File) sToFile.invoke(resultPath);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Indicates if a symlunk target exists
     *
     * @param file The symlink file
     * @return true if the target exists
     * @throws IOException upon error
     */
    private static boolean exists(File file)
            throws IOException {
        try {
            Object path = sToPath.invoke(file);
            final Boolean result = (Boolean) sExists.invoke(null, path, sEmptyLinkOpts);
            return result.booleanValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw (RuntimeException) e.getTargetException();
        }

    }

    /**
     * Creates a symbolic link
     *
     * @param symlink The symlink to create
     * @param target  Where it should point
     * @return The newly created symlink
     * @throws IOException upon error
     */
    public static File createSymbolicLink(File symlink, File target)
            throws IOException {
        try {
            if (!exists(symlink)) {
                Object link = sToPath.invoke(symlink);
                Object path = sCreateSymlink.invoke(null, link, sToPath.invoke(target), sEmptyFileAttributes);
                return (File) sToFile.invoke(path);
            }
            return symlink;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            final Throwable targetException = e.getTargetException();
            throw (IOException) targetException;
        }

    }

    /**
     * Performs a nio delete
     *
     * @param file the file to delete
     * @throws IOException Upon error
     */
    public static void delete(File file)
            throws IOException {
        try {
            Object path = sToPath.invoke(file);
            sDelete.invoke(null, path);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw (IOException) e.getTargetException();
        }
    }

    /**
     * Indicates if the current vm has java7 lubrary support
     *
     * @return true if java7 library support
     */
    public static boolean isAtLeastJava7() {
        return IS_JAVA7;
    }

}
