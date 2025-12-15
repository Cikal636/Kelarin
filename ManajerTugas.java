import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ManajerTugas {
    private List<Tugas> daftarTugas = new ArrayList<>();

    // Tambah tugas baru
    public void tambahTugas(Tugas tugas) {
        daftarTugas.add(tugas);
    }

    // Edit tugas berdasarkan index
    public void editTugas(int index, Tugas tugasBaru) {
        if (index >= 0 && index < daftarTugas.size()) {
            daftarTugas.set(index, tugasBaru);
        }
    }

    // Hapus tugas berdasarkan index
    public void hapusTugas(int index) {
        if (index >= 0 && index < daftarTugas.size()) {
            daftarTugas.remove(index);
        }
    }

    // Sorting berdasarkan deadline
    public void sortByDeadline() {
        daftarTugas.sort(Comparator.comparing(Tugas::getDeadline));
    }

    // Sorting berdasarkan prioritas
    public void sortByPrioritas() {
        daftarTugas.sort(Comparator.comparing(Tugas::getPrioritas));
    }

    // Getter daftar tugas
    public List<Tugas> getDaftarTugas() {
        return daftarTugas;
    }

    // Update status selesai
    public void setSelesai(int index, boolean selesai) {
        if (index >= 0 && index < daftarTugas.size()) {
            daftarTugas.get(index).setSelesai(selesai);
        }
    }

    // ✅ Tambahan: tampilkan semua tugas ke console
    public void tampilkanTugas() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int i = 0;
        for (Tugas t : daftarTugas) {
            System.out.printf("[%d] Judul: %s | Deskripsi: %s | Prioritas: %s | Deadline: %s | Selesai: %s%n",
                    i,
                    t.getJudul(),
                    t.getDeskripsi(),
                    t.getPrioritas(),
                    t.getDeadline().format(formatter),
                    t.isSelesai() ? "Ya" : "Belum");
            i++;
        }
    }
}