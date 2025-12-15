public class TugasKhusus extends Tugas {
    private String catatanTambahan;

    public TugasKhusus(String judul, String deskripsi, Prioritas prioritas, String deadline, String catatanTambahan) throws DeadlineInvalidException {
        super(judul, deskripsi, prioritas, deadline);
        this.catatanTambahan = catatanTambahan;
    }

    public String getCatatanTambahan() {
        return catatanTambahan;
    }
}