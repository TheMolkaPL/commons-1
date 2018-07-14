/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.commons.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {
    private FileUtils() {
    }

    /**
     * Create file directory, and then create file.
     *
     * @param file file to create.
     *
     * @return true if file was created and false if it already existed.
     *
     * @throws IOException from {@link File#createNewFile()}
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }
        file.getAbsoluteFile().getParentFile().mkdirs();
        return file.createNewFile();
    }

    /**
     * Read contents of given file as string.
     *
     * @param file file to read
     *
     * @return file content as string
     *
     * @throws IOException if read operation fails.
     */
    public static String asString(Path file) throws IOException {
        return new String(Files.readAllBytes(file), Charset.defaultCharset());
    }

    /**
     * Read contents of given file as string.
     *
     * @param file file to read
     * @param charset charset to use
     *
     * @return file content as string
     *
     * @throws IOException if read operation fails.
     */
    public static String asString(Path file, Charset charset) throws IOException {
        return new String(Files.readAllBytes(file), charset);
    }
}
