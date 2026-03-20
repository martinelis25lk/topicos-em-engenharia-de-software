interface Props {

    label : string
    factoryValue: number
    currentValue: number

}



export function PerformanceBar({

    label,
    factoryValue,
    current_value
}: Props){

    const gain = current_Value - factoryValue
    
    const percent = ((current_value - factoryValue) / factoryValue) * 100

    return (

  <div>

   <p>{label}</p>

   <div className="bar">

    <div
      style={{width: `${Math.abs(percent)}%`}}
      className={gain >= 0 ? "green" : "red"}
    />

   </div>

  </div>

 )

}