
type EditDateOfArrivalProps = {
    newDateOfArrival : string
    setNewDateOfArrival: (newDateOfArrival: string) => void
}

export default function EditDateOfArrival({newDateOfArrival, setNewDateOfArrival}: EditDateOfArrivalProps) {
    return (
        <div className="to-change">
            <p>When are you planning to go?</p>
            <input
                className="input-field"
                type={"text"}
                value={newDateOfArrival}
                placeholder={"choose a date"}
                onChange={event => setNewDateOfArrival(event.target.value)}
            />
        </div>
    )
}