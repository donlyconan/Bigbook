
package bigbook.transfer.buffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Donly conan design 16/12/2019
 * Message buffer implement form buffer
 * @author donly
 *
 */
public class MessageBuffer implements Buffer {
	// local write in array
	private int pos;

	// local limit data
	private int limit;

	// total size data
	private int cap;

	// current Partition
	private int curPar;

	// Att contains data
	private byte[] buffer;

	// partition data
	private List<Integer> pars;

	private boolean fliped = false;

	private MessageBuffer(byte[] data) {
		buffer = data;
		cap = limit = data.length;
		pos = 0;
		curPar = 0;
		pars = new ArrayList<Integer>();
	}

	private MessageBuffer(int cap) {
		buffer = new byte[cap];
		this.cap = limit = cap;
		pos = 0;
		curPar = 0;
		pars = new ArrayList<Integer>();
	}

	public static MessageBuffer wrap(byte[] data) {
		MessageBuffer message = new MessageBuffer(data);
		return message;
	}

	public static MessageBuffer allocate(int cap) {
		MessageBuffer message = new MessageBuffer(cap);
		return message;
	}

	@Override
	public void put(byte[] data) {
		this.put(data, 0, data.length);
	}

	@Override
	public void put(byte[] data, int off, int lenght) {
		if (pos + (lenght - off) < limit && off < lenght) {
			int localS = pos, localE = pos + (lenght - off);

			while (off < lenght) {
				buffer[pos] = data[off];
				off++;
				pos++;
			}

			if (!pars.isEmpty() && localS < pars.get(pars.size() - 1)) {
				repalcePartition(localS, localE);
			} else
				pars.add(pos);
		} else
			throw new ArrayIndexOutOfBoundsException((pos + lenght - off) + " >= " + limit + "  or" + " off >= lenght");
	}

	public void repalcePartition(int start, int end) {
		if (start == 0) {
			if (end == 0) {
				return;
			} else {
				pars.removeIf(e -> (e <= end));
				pars.add(0, end);
			}
		} else {
			int local = 0;
			for (int i = 0; i < pars.size(); i++) {
				int x = pars.get(i);

				if (x >= start && x <= end)
					pars.remove(x);
				
				if (local == 0 && x >= start)
					local = i;
			}

			pars.add(local, start);
			pars.add(local + 1, end);
		}
	}

	@Override
	public void flip() {
		limit = pos;
		pos = curPar = 0;
		fliped = true;
	}

	@Override
	public void get(byte[] data) {
		get(data, 0, data.length);
	}

	@Override
	public void get(byte[] data, int start, int end) {
		if (pos + (end - start) < limit && start < end) {

			while (start < end) {
				data[start] = buffer[pos];
				pos++;
				start++;
			}
		} else
			throw new ArrayIndexOutOfBoundsException(end + "LARGE Or " + start + " > " + end);
	}

	@Override
	public byte[] get(int start, int end) {
		if (end >= limit && start < end)
			throw new ArrayIndexOutOfBoundsException(end);

		byte[] data = new byte[end - start];
		int in = 0;

		while (start < end)
			data[in++] = buffer[start++];
		return data;
	}

	@Override
	public int remaining() {
		return limit - pos;
	}

	@Override
	public boolean hasNext() {
		return curPar < pars.size();
	}

	@Override
	public byte[] next() {
		int start, end, size = 0;

		if (curPar == 0) {
			size = pars.get(curPar);
			start = 0;
			end = size;
		} else if (curPar > 0 && curPar < pars.size()) {
			size = pars.get(curPar) - pars.get(curPar - 1);
			start = pars.get(curPar - 1);
			end = pars.get(curPar);
		} else
			return null;

		byte data[] = new byte[size];
		int temp = 0;

		while (start < end) {
			data[temp] = buffer[start];
			temp++;
			start++;
		}

		curPar++;
		return data;
	}

	@Override
	public void reset() {
		pos = 0;
		limit = cap;
		fliped = false;
		pars.clear();
	}

	public boolean isFliped() {
		return fliped;
	}

	public void setFliped(boolean fliped) {
		this.fliped = fliped;
	}

	@Override
	public String toString() {
		return "MessageBuffer [pos=" + pos + ", limit=" + limit + ", cap=" + cap + ", curPar=" + curPar + ", buffer="
				+ Arrays.toString(buffer) + ", pars=" + pars + "]";
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public int getCurPar() {
		return curPar;
	}

	public void setCurPar(int curPar) {
		this.curPar = curPar;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public List<Integer> getPars() {
		return pars;
	}

	public void setPars(List<Integer> pars) {
		this.pars = pars;
	}

}
