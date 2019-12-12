package bigbook.transfer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class DataWriter {

    private List<Message> writeQueue   = new ArrayList<>();
    private Message  messageInProgress = null;
    private int      bytesWritten      =    0;

    public DataWriter() {
    }

    public void write(SocketChannel socket, ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put(this.messageInProgress.sharedArray, this.messageInProgress.offset + this.bytesWritten, this.messageInProgress.length - this.bytesWritten);
        byteBuffer.flip();

        this.bytesWritten += socket.write(byteBuffer);
        byteBuffer.clear();

        if(bytesWritten >= this.messageInProgress.length){
            if(this.writeQueue.size() > 0){
                this.messageInProgress = this.writeQueue.remove(0);
            } else {
                this.messageInProgress = null;
                //todo unregister from selector
            }
        }
    }

    public boolean isEmpty() {
        return this.writeQueue.isEmpty() && this.messageInProgress == null;
    }

}
