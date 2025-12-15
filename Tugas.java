import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Tugas {
    private String judul;
    private String deskripsi;
    private Prioritas prioritas;
    private LocalDate deadline;
    private boolean selesai; // ✅ status selesai

    public Tugas(String judul, String deskripsi, Prioritas prioritas, String deadline) throws DeadlineInvalidException {
    this.judul = judul;
    this.deskripsi = deskripsi;
    this.prioritas = prioritas;
    this.selesai = false; // default belum selesai

    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDeadline = LocalDate.parse(deadline, formatter);

        // ✅ Validasi: deadline tidak boleh sebelum hari ini
        if (parsedDeadline.isBefore(LocalDate.now())) {
            throw new DeadlineInvalidException("Deadline tidak boleh sebelum hari ini!");
        }

        this.deadline = parsedDeadline;
    } catch (DateTimeParseException e) {
        throw new DeadlineInvalidException("Format deadline harus dd-MM-yyyy!");
    }
}

    // Getter & Setter
    public String getJudul() { return judul; }
    public String getDeskripsi() { return deskripsi; }
    public Prioritas getPrioritas() { return prioritas; }
    public LocalDate getDeadline() { return deadline; }
    public boolean isSelesai() { return selesai; }

    public void setJudul(String judul) { this.judul = judul; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public void setPrioritas(Prioritas prioritas) { this.prioritas = prioritas; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setSelesai(boolean selesai) { this.selesai = selesai; }
}